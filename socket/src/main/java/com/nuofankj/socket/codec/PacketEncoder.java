package com.nuofankj.socket.codec;

import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xifanxiaxue
 * @date 5/18/19
 * @desc
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    private PacketCodeC packetCodeC;

    public PacketEncoder(PacketCodeC packetCodeC) {
        this.packetCodeC = packetCodeC;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        this.packetCodeC.encode(out, packet);
    }
}
