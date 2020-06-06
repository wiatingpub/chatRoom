package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 7:58
 * @desc 心跳
 */
public class HeartBeatMessage extends AbstractMessage {

    public HeartBeatMessage() {
        super(MessageIdEnum.HEART_BEAT.getMessageId());
    }
}
