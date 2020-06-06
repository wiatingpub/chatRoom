package com.nuofankj.chatRoom.facade;

import com.nuofankj.chatRoom.proto.GroupChatMessage;
import com.nuofankj.chatRoom.service.ChatServiceImpl;
import com.nuofankj.socket.dispatcher.anno.Api;
import com.nuofankj.socket.manager.ChannelSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:19
 * @desc
 */
@Slf4j
@Controller
public class ChatFacade {

    @Autowired
    private ChatServiceImpl chatService;

    @Api
    public void chatGroup(ChannelSession channelSession, GroupChatMessage groupChatMessage) {
        chatService.chatGroupMessage(channelSession, groupChatMessage.getMessage());
    }
}
