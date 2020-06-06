package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;

/**
 * @author xifanxiaxue
 * @date 2020/6/4 8:31
 * @desc 广播的群聊消息
 */
public class BroadcastGroupChatMessage extends AbstractMessage {

    /**
     * 连接id
     */
    private int channelId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 当前时间
     */
    private String time;

    /**
     * 消息
     */
    private String message;

    public BroadcastGroupChatMessage(int channelId, String nickName, String time, String message) {
        super(MessageIdEnum.BROADCAST_CHAT_MESSAGE.getMessageId());

        this.channelId = channelId;
        this.nickName = nickName;
        this.time = time;
        this.message = message;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
