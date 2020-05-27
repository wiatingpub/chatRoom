package com.nuofankj.socket.protocol;


/**
 * @author xifanxiaxue
 * @date 2/7/20
 * @desc
 */
public class PingPacket extends Packet {
    @Override
    public int getCommand() {
        return 1;
    }
}
