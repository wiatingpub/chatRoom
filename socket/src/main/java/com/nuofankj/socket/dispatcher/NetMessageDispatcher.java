package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.server.MessageBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 2/9/20
 * @desc
 */
public class NetMessageDispatcher implements MessageDispatcher {
    
    private Map<Integer, MessageBean> commandMap = new HashMap<>();

    public Map<Integer, MessageBean> getCommandMap() {
        return commandMap;
    }

    @Override
    public void channelOpened(NetConnection netConnection) {
        log.info("连接[{}]打开", netConnection);
    }

    @Override
    public void channelClosed(NetConnection netConnection) {
        log.info("连接[{}]关闭", netConnection);
    }
}
