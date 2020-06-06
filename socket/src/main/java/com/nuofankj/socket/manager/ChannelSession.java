package com.nuofankj.socket.manager;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 9:53
 * @desc 连接session
 */
@Data
public class ChannelSession {

    private static AtomicInteger channelGender = new AtomicInteger(1000);
    /**
     * 是否认证
     */
    private boolean isAuth = false;
    /**
     * 登录时间
     */
    private long time = 0;
    /**
     * 连接id
     */
    private int channelId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 地址
     */
    private String address;
    /**
     * 具体channel
     */
    private Channel channel;

    public void updateChannelId() {
        this.setChannelId(channelGender.incrementAndGet());
    }
}
