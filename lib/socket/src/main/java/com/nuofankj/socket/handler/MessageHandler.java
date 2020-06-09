package com.nuofankj.socket.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuofankj.socket.dispatcher.MessageDispatcher;
import com.nuofankj.socket.dispatcher.bean.MessageBean;
import com.nuofankj.socket.event.LogoutEvent;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import com.nuofankj.socket.proto.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xifanxiaxue
 * @date 2020/5/30 16:36
 * @desc
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private ApplicationEventPublisher applicationEventPublisher;

    public MessageHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        ChannelSession channelSession = ChannelManager.getChannelSession(ctx.channel());
        if (channelSession == null) {
            log.error("连接{}找不到对应session", ctx.channel());
            return;
        }

        String message = frame.text();
        JSONObject json = JSONObject.parseObject(message);
        int code = json.getInteger("code");
        MessageBean messageBean = MessageDispatcher.id2MessageBeanMap.get(code);
        if (messageBean == null) {
            log.error("协议号:{}找不到分发对象", code);
        } else {
            AbstractMessage abstractMessage = JSON.parseObject(message, messageBean.getMessage());
            Method targetMethod = messageBean.getTargetMethod();
            targetMethod.setAccessible(true);
            try {
                targetMethod.invoke(messageBean.getTargetObject(), channelSession, abstractMessage);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        ctx.fireChannelRead(frame.retain());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ChannelSession channelSession = ChannelManager.removeChannel(ctx.channel());
        if (channelSession != null) {
            applicationEventPublisher.publishEvent(new LogoutEvent(this, channelSession.getNickName()));
        }

        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ChannelSession channelSession = ChannelManager.removeChannel(ctx.channel());
        if (channelSession != null) {
            applicationEventPublisher.publishEvent(new LogoutEvent(this, channelSession.getNickName()));
        }
    }
}
