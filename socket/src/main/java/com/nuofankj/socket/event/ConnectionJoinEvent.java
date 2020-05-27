package com.nuofankj.socket.event;

import com.nuofankj.socket.dispatcher.NetConnection;

/**
 * @author xifanxiaxue
 * @date 2/5/20
 * @desc
 */
public class ConnectionJoinEvent {

    private NetConnection netConnection;

    public ConnectionJoinEvent(NetConnection netConnection) {
        this.netConnection = netConnection;
    }
    
    public NetConnection getNetConnection() {
        return netConnection;
    }
}
