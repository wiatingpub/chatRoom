package com.nuofankj.socket.dispatcher.bean;

import com.nuofankj.socket.proto.AbstractMessage;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:03
 * @desc 存放协议最终触发的相关对象
 */
@Getter
public class MessageBean {
    /**
     * 目标对象
     */
    private Object targetObject;

    /**
     * 目标方法
     */
    private Method targetMethod;

    /**
     * 协议类
     */
    private Class<AbstractMessage> message;

    public static MessageBean valueOf(Object targetObject, Method targetMethod, Class<AbstractMessage> message) {
        MessageBean messageBean = new MessageBean();
        messageBean.targetObject = targetObject;
        messageBean.targetMethod = targetMethod;
        messageBean.message = message;
        return messageBean;
    }
}
