package com.nuofankj.socket.handler;

import com.nuofankj.socket.event.LogoutEvent;
import com.nuofankj.socket.manager.ChannelManager;
import com.nuofankj.socket.manager.ChannelSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 9:32
 * @desc
 */
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private ApplicationEventPublisher applicationEventPublisher;

    public AuthHandler(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (event.state().equals(IdleState.READER_IDLE)) {
                ChannelSession channelSession = ChannelManager.removeChannel(ctx.channel());
                if (channelSession != null) {
                    applicationEventPublisher.publishEvent(new LogoutEvent(this, channelSession.getNickName()));
                }
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        ChannelManager.addChannel(ctx.channel());
        ctx.fireChannelRead(request.retain());
    }
}
