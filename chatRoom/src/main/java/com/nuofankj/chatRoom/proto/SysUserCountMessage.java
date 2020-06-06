package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;

/**
 * @author xifanxiaxue
 * @date 2020/6/6 9:35
 * @desc 当前登陆的用户个数
 */
public class SysUserCountMessage extends AbstractMessage {

    /**
     * 当前登陆的用户个数
     */
    private int count;

    /**
     * 当前状态改变的用户昵称
     */
    private String userNickName;

    /**
     * 1代表加入，-1代表退出
     */
    private int action;

    public SysUserCountMessage(int count, String userNickName, int action) {
        super(MessageIdEnum.SYS_USER_COUNT.getMessageId());
        this.count = count;
        this.userNickName = userNickName;
        this.action = action;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
