package com.nuofankj.socket.util;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 9:57
 * @desc netty辅助工具
 */
public class NettyUtil {

    /**
     * 获取Channel的远程IP地址
     *
     * @param channel
     * @return
     */
    public static String parseChannelRemoteAddress(Channel channel) {
        if (null == channel) {
            return "";
        }

        SocketAddress remote = channel.remoteAddress();
        final String address = remote != null ? remote.toString() : "";

        if (address.length() <= 0) {
            return "";
        }

        int index = address.lastIndexOf("/");
        if (index >= 0) {
            return address.substring(index + 1);
        }
        return address;
    }
}
