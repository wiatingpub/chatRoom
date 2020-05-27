package com.nuofankj.socket.bootstrap.model;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc
 */
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

    public int getListenPort() {
        return listenPort;
    }

    public int getBackLog() {
        return backLog;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public int getMaxFrameLength() {
        return maxFrameLength;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public boolean isKeepALive() {
        return keepALive;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public int getIdleTimeoutSeconds() {
        return idleTimeoutSeconds;
    }

    public int getAcceptorThreads() {
        return acceptorThreads;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public static class Builder {

        NetServerOptions serverOptions = new NetServerOptions();

        public Builder() {
            serverOptions = new NetServerOptions();
            serverOptions.connectTimeout = 10000;
            serverOptions.acceptorThreads = 1;
            serverOptions.workThreads = Runtime.getRuntime().availableProcessors();
            serverOptions.idleTimeoutSeconds = 3 * 60;
            serverOptions.tcpNoDelay = true;
            serverOptions.keepALive = true;
            serverOptions.maxFrameLength = 32 * 1024;
            serverOptions.receiveBufferSize = 32 * 1024;
            serverOptions.sendBufferSize = 32 * 1024;
            serverOptions.reuseAddress = true;
            serverOptions.backLog = 1024;
            serverOptions.listenPort = 8005;
        }

        public NetServerOptions build() {
            NetServerOptions serverOptions = this.serverOptions;
            this.serverOptions = null;
            return serverOptions;
        }
    }
}
