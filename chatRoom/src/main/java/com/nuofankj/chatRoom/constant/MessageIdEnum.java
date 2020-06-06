package com.nuofankj.chatRoom.constant;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:25
 * @desc
 */
public enum MessageIdEnum {
    /**
     * 申请登陆
     */
    APPLY_LOGIN(10000),

    /**
     * 心跳
     */
    HEART_BEAT(10016),

    /**
     * 群聊消息
     */
    GROUP_CHAT_MESSAGE(10086),

    /**
     * 当前登陆的用户个数
     */
    SYS_USER_COUNT(20001),

    /**
     * 登陆成功
     */
    LOGIN_SUCCESS(20002),

    /**
     * 广播群聊消息
     */
    BROADCAST_CHAT_MESSAGE(20004),
    ;

    MessageIdEnum(int messageId) {
        this.messageId = messageId;
    }

    private int messageId;

    public int getMessageId() {
        return messageId;
    }
}
