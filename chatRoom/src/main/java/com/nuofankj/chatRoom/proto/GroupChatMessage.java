package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:16
 * @desc 群聊协议
 */
public class GroupChatMessage extends AbstractMessage {

    private String message;

    public GroupChatMessage() {
        super(MessageIdEnum.GROUP_CHAT_MESSAGE.getMessageId());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
