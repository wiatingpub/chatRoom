package com.nuofankj.chatRoom.facade;

import com.nuofankj.chatRoom.constant.ChatRoomConstant;
import com.nuofankj.chatRoom.proto.ApplyLoginMessage;
import com.nuofankj.chatRoom.proto.HeartBeatMessage;
import com.nuofankj.chatRoom.proto.SysUserCountMessage;
import com.nuofankj.chatRoom.service.UserServiceImpl;
import com.nuofankj.socket.dispatcher.anno.Api;
import com.nuofankj.socket.event.LogoutEvent;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.util.PacketSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:22
 * @desc 用户相关
 */
@Slf4j
@Controller
public class UserFacade {

    @Autowired
    private UserServiceImpl userService;

    @Api
    public void login(ChannelSession session, ApplyLoginMessage applyLoginMessage) {
        userService.login(session, applyLoginMessage.getNickName());
    }

    @EventListener
    public void afterLogout(LogoutEvent event) {
        PacketSendUtil.broadcastMessage(new SysUserCountMessage(ChannelManager.getActiveUserCount(), event.getUserNickName(), ChatRoomConstant.LOGOUT_ACTION));
    }

    @Api
    public void heartBeat(ChannelSession session, HeartBeatMessage heartBeatMessage) {
    }
}
