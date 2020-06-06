package com.nuofankj.chatRoom.service;

import com.nuofankj.chatRoom.proto.BroadcastGroupChatMessage;
import com.nuofankj.chatRoom.utils.TimeUtil;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.util.PacketSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:19
 * @desc
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public void chatGroupMessage(ChannelSession session, String message) {
        boolean auth = session.isAuth();
        if (!auth) {
            log.error("连接:{}，没有登陆，无法群聊", session.getAddress());
            return;
        }

        log.info("用户：[{}-{}]广播群聊消息:{}", session.getChannelId(), session.getNickName(), message);
        PacketSendUtil.broadcastMessage(new BroadcastGroupChatMessage(session.getChannelId(), session.getNickName(), TimeUtil.getCurrentTime(), message));
    }
}
