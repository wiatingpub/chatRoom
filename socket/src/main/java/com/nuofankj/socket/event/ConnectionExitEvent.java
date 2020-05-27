package com.nuofankj.socket.event;

import com.nuofankj.socket.dispatcher.NetConnection;

/**
 * @author xifanxiaxue
 * @date 2/13/20
 * @desc
 */
public class ConnectionExitEvent {

    private NetConnection netConnection;

    public ConnectionExitEvent(NetConnection netConnection) {
        this.netConnection = netConnection;
    }

    public NetConnection getNetConnection() {
        return netConnection;
    }
}
