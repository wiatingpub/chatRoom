package com.nuofankj.chatRoom.service;

import com.nuofankj.chatRoom.constant.ChatRoomConstant;
import com.nuofankj.chatRoom.proto.LoginSuccessMessage;
import com.nuofankj.chatRoom.proto.SysUserCountMessage;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.util.PacketSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 0:28
 * @desc 用户相关
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void login(ChannelSession session, String nickName) {
        boolean isAuth = ChannelManager.activeSession(session.getChannel(), nickName);
        PacketSendUtil.sendMessage(session, new LoginSuccessMessage(isAuth));

        PacketSendUtil.broadcastMessage(new SysUserCountMessage(ChannelManager.getActiveUserCount(), nickName, ChatRoomConstant.JOIN_ACTION));
        log.info("用户：[{}-{}]登陆成功，校验状态：{}, 推送激活的链接个数：{}", session.getChannelId(), session.getNickName(), isAuth, ChannelManager.getActiveUserCount());
    }
}
