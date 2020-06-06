package com.nuofankj.socket.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author xifanxiaxue
 * @date 2020/6/6 10:08
 * @desc 登出事件
 */
public class LogoutEvent extends ApplicationEvent {

    private String userNickName;

    public LogoutEvent(Object source, String userNickName) {
        super(source);
        this.userNickName = userNickName;
    }

    public String getUserNickName() {
        return userNickName;
    }
}
