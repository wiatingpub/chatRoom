package com.nuofankj.socket.server;

import lombok.Getter;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc
 */
@Getter
public class NetServerOptions {

    private int listenPort;

    /**
     * BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
     */
    private int backLog;

    /**
     * 表示客户端调用服务端接口的超时时间
     */
    private int connectTimeout;

    /**
     * 数据发送缓冲区的大小
     */
    private int sendBufferSize;

    /**
     * 数据接受缓冲区的大小
     */
    private int receiveBufferSize;

    private int maxFrameLength;

    /**
     * 禁用nagle算法
     */
    private boolean tcpNoDelay;

    /**
     * 当设置为true的时候，TCP会实现监控连接是否有效，当连接处于空闲状态的时候，超过了2个小时，本地的TCP实现会发送一个数据包给远程的 socket，如果远程没有发回响应，TCP会持续尝试11分钟，知道响应为止，如果在12分钟的时候还没响应，TCP尝试关闭socket连接。
     */
    private boolean keepALive;

    private boolean reuseAddress;

    private int idleTimeoutSeconds;

    private int acceptorThreads = 0;

    private int workThreads = 20;

    private String webSocketPath;

    /**
     * 水位监控,用是限制netty向channel写数据时使用的缓冲区边界。当netty需要往channel中写入数据时会先把数据写入一个Buffer缓冲区，这个缓冲区是各个channel独占的，不共享。等到channel空闲的时候就从缓冲区中读取数据进行发送。这样做可以提高网络的吞吐量。但也带来了一个缺点，就是在碰到对端非常慢(对端慢指的是对端处理TCP包的速度变慢，比如对端负载特别高的时候，这个时候如果还是不断地写数据，这个buffer就会不断地增长，最后就会由于占用大量的内存引起服务器处理缓慢，进而可能引起崩溃。这时WRITE_BUFFER_WATER_MARK就派上用场了，它规定了高低水位线。当Buffer的数据超过高水位线时就停止写入数据，设置channel的isWritable为false。等到buffer中的数据由于被消费而低于低水位线时设置channel的isWritable为true，又可以重新接受写入的数据。所以设置了这个参数之后，对应用的要求是，每次写数据时先判断channel的isWritable，在 true时才进行写入。
     */
    private int waterMarkLow;

    private int waterMarkHigh;

    public static class Builder {

        NetServerOptions serverOptions = new NetServerOptions();

        public Builder(int listenPort) {
            serverOptions = new NetServerOptions();
            serverOptions.connectTimeout = 10000;
            serverOptions.acceptorThreads = Runtime.getRuntime().availableProcessors();
            serverOptions.workThreads = Runtime.getRuntime().availableProcessors();
            serverOptions.idleTimeoutSeconds = 3 * 60;
            serverOptions.tcpNoDelay = true;
            serverOptions.keepALive = true;
            serverOptions.maxFrameLength = 32 * 1024;
            serverOptions.receiveBufferSize = 32 * 1024;
            serverOptions.sendBufferSize = 32 * 1024;
            serverOptions.reuseAddress = true;
            serverOptions.backLog = 1024;
            serverOptions.listenPort = listenPort;
            serverOptions.webSocketPath = "/websocket";
            serverOptions.waterMarkLow = 32 * 1024;
            serverOptions.waterMarkHigh = 128 * 1024;
        }

        public NetServerOptions build() {
            NetServerOptions serverOptions = this.serverOptions;
            this.serverOptions = null;
            return serverOptions;
        }
    }
}
