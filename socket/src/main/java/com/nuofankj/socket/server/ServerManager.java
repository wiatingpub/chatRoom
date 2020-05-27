package com.nuofankj.socket.server;

import com.nuofankj.socket.dispatcher.NetConnection;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xifanxiaxue
 * @date 2/9/20
 * @desc
 */
public class ServerManager {

    /**
     * 所有连接
     */
    public static List<NetConnection> allConnections = new CopyOnWriteArrayList<>();

    public static List<NetConnection> getAllConnections() {
        return allConnections;
    }

    public static NetConnection getNetConnection(Channel channel) {

        for (NetConnection netConnection : allConnections) {
            if (netConnection.getChannel() == channel) {
                return netConnection;
            }
        }

        return null;
    }

    public static void removeChannel(NetConnection netConnection) {

        allConnections.remove(netConnection);
    }
}
