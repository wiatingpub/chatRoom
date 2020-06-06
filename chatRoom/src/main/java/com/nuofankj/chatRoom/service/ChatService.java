package com.nuofankj.chatRoom.service;

import com.nuofankj.socket.manager.ChannelSession;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:19
 * @desc
 */
public interface ChatService {

    /**
     * 群聊
     *
     * @param channelSession
     * @param message
     */
    void chatGroupMessage(ChannelSession channelSession, String message);
}
