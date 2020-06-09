package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.dispatcher.bean.MessageBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 2020/6/2 8:13
 * @desc 协议分发
 */
public class MessageDispatcher {

    /**
     * 协议id->MessageBean
     */
    public static Map<Integer, MessageBean> id2MessageBeanMap = new HashMap<>();
}
