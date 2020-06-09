package com.nuofankj.socket.manager;

import com.nuofankj.socket.util.NettyUtil;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 9:52
 * @desc 连接管理
 */
@Slf4j
public class ChannelManager {

    /**
     * 存放所有Channel相关的引用
     */
    public static ConcurrentHashMap<Channel, ChannelSession> sessionMap = new ConcurrentHashMap<>();

    /**
     * 缓存激活的session个数
     */
    private static AtomicInteger sessionCount = new AtomicInteger(0);

    public static void addChannel(Channel channel) {
        ChannelSession channelSession = new ChannelSession();
        channelSession.setAddress(NettyUtil.parseChannelRemoteAddress(channel));
        channelSession.setChannel(channel);
        channelSession.setTime(System.currentTimeMillis());
        sessionMap.put(channel, channelSession);
        log.info("添加地址:{}的连接", channelSession.getAddress());
    }

    public static ChannelSession removeChannel(Channel channel) {
        ChannelSession channelSession = sessionMap.remove(channel);
        if (channelSession != null) {
            log.info("移除连接:[{}-{}]，当前连接个数:{}", channelSession.getNickName(), channelSession.getChannelId(), sessionCount.get());
            sessionCount.decrementAndGet();
        }
        return channelSession;
    }

    /**
     * 根据Channel取得对应ChannelSession
     *
     * @param channel
     * @return
     */
    public static ChannelSession getChannelSession(Channel channel) {
        return sessionMap.get(channel);
    }

    /**
     * 激活session
     *
     * @param channel
     * @param nickName
     * @return
     */
    public static boolean activeSession(Channel channel, String nickName) {
        ChannelSession channelSession = ChannelManager.getChannelSession(channel);
        if (channelSession == null) {
            return false;
        }

        sessionCount.incrementAndGet();

        channelSession.setNickName(nickName);
        channelSession.setAuth(true);
        channelSession.setTime(System.currentTimeMillis());
        channelSession.updateChannelId();

        log.info("激活连接:[{}-{}]", nickName, channelSession.getChannelId());
        return true;
    }

    /**
     * 激活的用户个数
     *
     * @return
     */
    public static int getActiveUserCount() {
        return ChannelManager.sessionCount.get();
    }
}
