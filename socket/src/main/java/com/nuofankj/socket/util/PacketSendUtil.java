package com.nuofankj.socket.util;

import com.nuofankj.socket.dispatcher.NetConnection;
import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.server.ServerManager;

import java.util.List;

/**
 * @author xifanxiaxue
 * @date 2/9/20
 * @desc
 */
public class PacketSendUtil {

    public static void sendPacket(NetConnection connection, Packet packet) {
        connection.sendMessage(packet);
    }

    public static void sendToAll(Packet packet) {
        List<NetConnection> allConnections = ServerManager.getAllConnections();
        for (NetConnection netConnection : allConnections) {
            netConnection.sendMessage(packet);
        }
    }

    public static void sendToAllNotSelf(NetConnection connection, Packet packet) {
        List<NetConnection> allConnections = ServerManager.getAllConnections();
        for (NetConnection netConnection : allConnections) {
            if (connection != netConnection) {
                netConnection.sendMessage(packet);
            }
        }
    }
}
