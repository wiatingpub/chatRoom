package com.nuofankj.chatRoom.service;

import com.nuofankj.socket.manager.ChannelSession;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 0:28
 * @desc
 */
public interface UserService {

    /**
     * 用户登陆
     *
     * @param session
     * @param nickName
     */
    void login(ChannelSession session, String nickName);
}
