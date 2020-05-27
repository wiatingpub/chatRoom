package com.nuofankj.socket.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.annotation.PostConstruct;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    @JSONField(serialize = false)
    public abstract int getCommand();
}
