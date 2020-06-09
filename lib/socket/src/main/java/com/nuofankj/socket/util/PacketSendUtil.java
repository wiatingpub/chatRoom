package com.nuofankj.socket.util;

import com.alibaba.fastjson.JSON;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.proto.AbstractMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 8:09
 * @desc 协议推送工具
 */
public class PacketSendUtil {

    /**
     * 给session推送协议
     *
     * @param session
     * @param message
     */
    public static void sendMessage(ChannelSession session, AbstractMessage message) {
        session.getChannel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
    }

    public static void broadcastMessage(AbstractMessage message) {
        for (Map.Entry<Channel, ChannelSession> sessionEntry : ChannelManager.sessionMap.entrySet()) {
            ChannelSession targetSession = sessionEntry.getValue();
            if (targetSession.isAuth()) {
                sendMessage(targetSession, message);
            }
        }
    }
}
