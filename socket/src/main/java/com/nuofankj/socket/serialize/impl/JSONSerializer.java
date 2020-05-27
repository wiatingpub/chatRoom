package com.nuofankj.socket.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.nuofankj.socket.serialize.Serializer;
import com.nuofankj.socket.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {

    @Override
    public int getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, String jsonMsg) {
        return JSON.parseObject(jsonMsg, clazz);
    }
}
