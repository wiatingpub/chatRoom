package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:23
 * @desc 登陆协议
 */
public class ApplyLoginMessage extends AbstractMessage {

    private String nickName;

    public ApplyLoginMessage() {
        super(MessageIdEnum.APPLY_LOGIN.getMessageId());
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
