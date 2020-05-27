package com.nuofankj.socket.protocol;

import com.nuofankj.socket.constant.BasePacketCommandEnum;
import com.nuofankj.socket.constant.SocketConstant;
import com.nuofankj.socket.dispatcher.NetMessageDispatcher;
import com.nuofankj.socket.serialize.Serializer;
import com.nuofankj.socket.serialize.impl.JSONSerializer;
import com.nuofankj.socket.server.MessageBean;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    private final Map<Integer, Serializer> serializerMap;
    private NetMessageDispatcher netMessageDispatcher;


    public PacketCodeC(NetMessageDispatcher netMessageDispatcher) {

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
        this.netMessageDispatcher = netMessageDispatcher;
    }

    /**
     * 编码，此处将要发出去的数据包通过自定义规则放入ByteBuff中，主要目的是解决拆包和黏包的问题
     *
     * @param byteBuf
     * @param packet
     */
    public void encode(ByteBuf byteBuf, Packet packet) {

        // 序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 存放魔数，此处占据 4 个字节
        byteBuf.writeInt(SocketConstant.MAGIC_NUMBER);
        // 存放协议版本，此处占据 1 个字节
        byteBuf.writeByte(packet.getVersion());
        // 存放序列化算法，此处占据 1 个字节
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        // 存放协议标志id，此处占据 1 个字节
        byteBuf.writeByte(packet.getCommand());
        // 存放序列化后得到的字节流大小，此处占据 4 个字节
        byteBuf.writeInt(bytes.length);
        // 存放序列化后的字节流
        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码，此处主要通过编码规则将ByteBuff解码出实际推送的数据对象
     *
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 魔数
        byteBuf.skipBytes(4);

        // 跳过 版本号
        byteBuf.skipBytes(1);

        // 读取 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 读取 指令
        byte command = byteBuf.readByte();

        // 读取 数据包长度
        int length = byteBuf.readInt();

        // 读取 指定长度的字节流数据入bytes中
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 根据command 读取接收到的数据的对象类型
        Class<? extends Packet> requestType = getRequestType(command);
        // 根据序列化算法取出序列化器
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            // 进行反序列化
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }


    private Serializer getSerializer(int serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(int command) {

        MessageBean messageBean = netMessageDispatcher.getCommandMap().get(command);
        if (messageBean == null) {
            for (BasePacketCommandEnum basePacketCommandEnum : BasePacketCommandEnum.values()) {
                if (basePacketCommandEnum.commandId == command) {
                    return basePacketCommandEnum.packet;
                }
            }

        } else {
            return messageBean.getPacket();
        }

        return null;
    }
}
