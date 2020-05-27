package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.server.MessageBean;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.lang.reflect.Method;

/**
 * @author xifanxiaxue
 * @date 2/8/20
 * @desc
 */
public interface MessageDispatcher {

    InternalLogger log = InternalLoggerFactory.getInstance(MessageDispatcher.class);

    default void messageReceived(NetConnection netConnection, MessageBean messageBean, Packet packet) {

        try {
            Method method = messageBean.getMethod();
            method.setAccessible(true);
            method.invoke(messageBean.getBean(), netConnection, packet);
        } catch (Exception e) {
            log.error(netConnection.toString());
        }
    }

    void channelOpened(NetConnection netConnection);

    void channelClosed(NetConnection netConnection);
}
