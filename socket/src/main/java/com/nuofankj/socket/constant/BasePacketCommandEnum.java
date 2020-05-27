package com.nuofankj.socket.constant;

import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.protocol.PingPacket;
import com.nuofankj.socket.protocol.PongPacket;

/**
 * @author xifanxiaxue
 * @date 2/5/20
 * @desc
 */
public enum BasePacketCommandEnum {

    PING(1, PingPacket.class),

    PONG(2, PongPacket.class),;

    BasePacketCommandEnum(int commandId, Class<? extends Packet> packet) {
        this.commandId = commandId;
        this.packet = packet;
    }

    public int commandId;

    public Class<? extends Packet> packet;
}
