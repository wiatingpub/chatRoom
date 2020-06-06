package com.nuofankj.chatRoom.proto;

import com.nuofankj.chatRoom.constant.MessageIdEnum;
import com.nuofankj.socket.proto.AbstractMessage;
import lombok.Data;

/**
 * @author xifanxiaxue
 * @date 2020/6/3 8:12
 * @desc 登陆成功协议
 */
public class LoginSuccessMessage extends AbstractMessage {

    /**
     * 是否登陆成功
     */
    private boolean auth;

    public LoginSuccessMessage(boolean auth) {
        super(MessageIdEnum.LOGIN_SUCCESS.getMessageId());
        this.auth = auth;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}
