package com.nuofankj.socket.serialize;

import com.nuofankj.socket.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    int getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * json字符串转 java 对象
     *
     * @param clazz
     * @param jsonMsg
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, String jsonMsg);
}
