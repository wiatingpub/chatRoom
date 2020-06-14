package com.nuofankj.socket.server;

import com.nuofankj.socket.handler.AuthHandler;
import com.nuofankj.socket.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc WebSocket入口
 */
@Component
public class WebSocketServer implements SmartInitializingSingleton {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketServer.class);
    private NetServerOptions serverOptions;
    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel channel;
    @Value("${websocket.listenPort}")
    private int port;

    @Override
    public void afterSingletonsInstantiated() {
        this.serverOptions = new NetServerOptions.Builder(port).build();
        this.start();
    }

    private void start() {
        Class<? extends ServerChannel> serverChannel;
        if (isLinux()) {
            this.bossGroup = new EpollEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerAcceptorIoThread"));
            this.workerGroup = new EpollEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerWorkerIoThread"));
            serverChannel = EpollServerSocketChannel.class;
        } else {
            this.bossGroup = new NioEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerAcceptorIoThread"));
            this.workerGroup = new NioEventLoopGroup(serverOptions.getAcceptorThreads(), new DefaultThreadFactory("NetServerWorkerIoThread"));
            serverChannel = NioServerSocketChannel.class;
        }

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(this.bossGroup, this.workerGroup);
        serverBootstrap.channel(serverChannel);
        serverBootstrap.option(ChannelOption.SO_BACKLOG, serverOptions.getBackLog());
        serverBootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(serverOptions.getWaterMarkLow(), serverOptions.getWaterMarkHigh()));
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, serverOptions.isKeepALive());
        // 某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        // Socket参数，关闭Socket的延迟时间，默认值为-1，表示禁用该功能。-1表示socket.close()方法立即返回，但OS底层会将发送缓冲区全部发送到对端。0表示socket.close()方法立即返回，OS放弃发送缓冲区的数据直接向对端发送RST包，对端收到复位错误。非0整数值表示调用socket.close()方法的线程被阻塞直到延迟时间到或发送缓冲区中的数据发送完毕，若超时，则对端会收到复位错误。
        serverBootstrap.childOption(ChannelOption.SO_LINGER, 0);
        // Netty参数，用于Channel分配接受Buffer的分配器，默认值为AdaptiveRecvByteBufAllocator.DEFAULT，是一个自适应的接受缓冲区分配器，能根据接受到的数据自动调节大小。可选值为FixedRecvByteBufAllocator，固定大小的接受缓冲区分配器。
        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, serverOptions.isTcpNoDelay());
        serverBootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, serverOptions.getConnectTimeout());
        serverBootstrap.childOption(ChannelOption.SO_RCVBUF, serverOptions.getReceiveBufferSize());
        serverBootstrap.childOption(ChannelOption.SO_SNDBUF, serverOptions.getSendBufferSize());
        serverBootstrap.childHandler(createNetServerChannelInitializer(serverOptions.getWebSocketPath()));

        listen();
    }

    private ChannelInitializer<Channel> createNetServerChannelInitializer(String webSocketPath) {

        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                // 设计思路是，服务器在一定时间内发现连接读空闲，则再移除对应的channel，当然了客户端也要定时推送pong数据过来
                pipeline.addLast(new IdleStateHandler(60, 0, 0));
                pipeline.addLast(new AuthHandler(applicationEventPublisher));
                // WebSocketServerProtocolHandler 处理了所有委托管理的 WebSocket 帧类型以 及升级握手本身。如果握手成功，那么所需的ChannelHandler将会被添加到ChannelPipeline 中，而那些不再需要的ChannelHandler则将会被移除
                pipeline.addLast(new WebSocketServerProtocolHandler(webSocketPath));
                pipeline.addLast(new MessageHandler(applicationEventPublisher));
            }
        };
    }

    private void listen() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(inetSocketAddress).sync();
            if (channelFuture.isSuccess()) {
                this.channel = channelFuture.channel();
                log.info("服务器地址[{}]开启成功！！！", channel);
            }
        } catch (Throwable e) {
            shutdown();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void shutdown() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }

        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    private boolean isLinux() {

        String osName = System.getProperty("os.name");
        return osName != null && osName.toLowerCase().contains("linux");
    }
}

