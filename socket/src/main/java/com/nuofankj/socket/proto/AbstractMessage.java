package com.nuofankj.socket.proto;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 16:04
 * @desc 协议基类
 */
public abstract class AbstractMessage {

    private int code;

    public AbstractMessage(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
