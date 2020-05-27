package com.nuofankj.socket.server;

import com.nuofankj.socket.protocol.Packet;

import java.lang.reflect.Method;

/**
 * @author xifanxiaxue
 * @date 5/24/19
 * @desc
 */
public class MessageBean {

    private Object bean;

    private Method method;

    private Class<Packet> packet;

    public static MessageBean valueOf(Object bean, Method method, Class<Packet> packet) {

        MessageBean messageBean = new MessageBean();
        messageBean.bean = bean;
        messageBean.method = method;
        messageBean.packet = packet;
        return messageBean;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public Class<Packet> getPacket() {
        return packet;
    }
}
