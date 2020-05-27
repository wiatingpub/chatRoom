package com.nuofankj.socket.codec;

import com.nuofankj.socket.dispatcher.NetConnection;
import com.nuofankj.socket.dispatcher.NetMessageDispatcher;
import com.nuofankj.socket.event.ConnectionJoinEvent;
import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.protocol.PacketCodeC;
import com.nuofankj.socket.server.MessageBean;
import com.nuofankj.socket.server.ServerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author xifanxiaxue
 * @date 5/18/19
 * @desc
 */
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder {

    private ApplicationContext context;

    private NetMessageDispatcher messageDispatcher;

    private PacketCodeC packetCodeC;

    private static final AttributeKey<NetConnection> NET_CONNECTION = AttributeKey.valueOf("NET_CONNECTION");

    public PacketDecoder(ApplicationContext context, NetMessageDispatcher messageDispatcher, PacketCodeC packetCodeC) {
        this.context = context;
        this.messageDispatcher = messageDispatcher;
        this.packetCodeC = packetCodeC;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) {


        Packet decode = packetCodeC.decode(in);
        out.add(decode);

        NetConnection connection = getConnection(ctx.channel());
        if (messageDispatcher.getCommandMap().containsKey(decode.getCommand())) {
            MessageBean messageBean = messageDispatcher.getCommandMap().get(decode.getCommand());
            messageDispatcher.messageReceived(connection, messageBean, decode);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        if (!createConnection(ctx.channel())) {
            ctx.channel().close();
            return;
        }

        NetConnection netConnection = getConnection(ctx.channel());
        messageDispatcher.channelOpened(netConnection);
        ServerManager.getAllConnections().add(netConnection);

        context.publishEvent(new ConnectionJoinEvent(netConnection));
    }

    private boolean createConnection(Channel channel) {
        Attribute<NetConnection> connectionAttribute = channel.attr(NET_CONNECTION);
        return connectionAttribute.compareAndSet(null, new NetConnection(channel));
    }

    private NetConnection getConnection(Channel channel) {
        return channel.attr(NET_CONNECTION).get();
    }
}
