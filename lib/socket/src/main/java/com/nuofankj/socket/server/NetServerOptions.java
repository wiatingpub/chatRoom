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

    private int backLog;

    // 连接超时时间
    private int connectTimeout;

    private int sendBufferSize;

    private int receiveBufferSize;

    private int maxFrameLength;

    private boolean tcpNoDelay;

    private boolean keepALive;

    private boolean reuseAddress;

    private int idleTimeoutSeconds;

    private int acceptorThreads = 0;

    private int workThreads = 20;

    private String webSocketPath;

    public static class Builder {

        NetServerOptions serverOptions = new NetServerOptions();

        public Builder() {
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
            serverOptions.listenPort = 80;
            serverOptions.webSocketPath = "/websocket";
        }

        public NetServerOptions build() {
            NetServerOptions serverOptions = this.serverOptions;
            this.serverOptions = null;
            return serverOptions;
        }
    }
}
