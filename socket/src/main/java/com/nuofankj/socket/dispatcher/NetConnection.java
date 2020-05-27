package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.protocol.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc
 */
public class NetConnection {

    private volatile Channel channel;

    private String remoteAddress;

    private String remoteIp;

    private ConcurrentHashMap<String, Object> attributes = new ConcurrentHashMap<>();

    private final InternalLogger log = InternalLoggerFactory.getInstance(NetConnection.class);

    public NetConnection(Channel channel) {
        setChannel(channel);
    }

    private void writeAndFlush(Channel selfChannel, Packet packet) {

        selfChannel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    log.error("消息[{}]发送给地址[{}]失败，异常[{}]", packet.getCommand(), remoteAddress, future.cause());
                }
            }
        });
    }

    public void sendMessage(Packet packet) {
        Channel channel = this.channel;
        if (invalidChannel(channel)) {
            return;
        }

        writeAndFlush(channel, packet);
    }

    private boolean invalidChannel(Channel channel) {
        return channel == null || !channel.isActive();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        if (channel != null) {
            this.remoteAddress = channel.remoteAddress().toString();
            if (this.remoteAddress.length() > 0) {
                int index = remoteAddress.lastIndexOf("/");
                if (index >= 0) {
                    String remoteIpAndPort = remoteAddress.substring(index + 1);
                    String[] address = remoteIpAndPort.split(":");
                    this.remoteIp = address[0];
                }
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    @Override
    public String toString() {
        return "NetConnection{" +
                "channel=" + channel +
                ", remoteAddress='" + remoteAddress + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                '}';
    }
}
