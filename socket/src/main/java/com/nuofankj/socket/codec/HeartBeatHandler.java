package com.nuofankj.socket.codec;

import com.nuofankj.socket.dispatcher.NetConnection;
import com.nuofankj.socket.event.ConnectionExitEvent;
import com.nuofankj.socket.event.ConnectionJoinEvent;
import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.protocol.PingPacket;
import com.nuofankj.socket.protocol.PongPacket;
import com.nuofankj.socket.server.ServerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.net.InetSocketAddress;

@Slf4j
public class HeartBeatHandler extends SimpleChannelInboundHandler<Packet> {

    private ApplicationContext applicationContext;

    public HeartBeatHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        if (packet instanceof PingPacket) {
            ctx.channel().writeAndFlush(new PongPacket());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        IdleStateEvent event = (IdleStateEvent) evt;
        if (event.state() == IdleState.READER_IDLE) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            ctx.channel().writeAndFlush("OUT");
            log.info("[{}]读空闲，关闭连接", inetSocketAddress.getAddress().getHostAddress());
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        shutUpConnection(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        shutUpConnection(ctx);
    }

    private void shutUpConnection(ChannelHandlerContext ctx) {

        NetConnection netConnection = ServerManager.getNetConnection(ctx.channel());
        if (netConnection != null) {
            log.info(" [{}]连接异常", netConnection.getRemoteIp());
            ServerManager.removeChannel(netConnection);

            applicationContext.publishEvent(new ConnectionExitEvent(netConnection));
        }
    }
}