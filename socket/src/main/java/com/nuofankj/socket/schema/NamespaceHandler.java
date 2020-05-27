package com.nuofankj.socket.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author xifanxiaxue
 * @date 2019/04/10
 * @desc SOCKET连接 命名空间注册器
 */
public class NamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(SchemaNames.CONFIG_ELEMENT, new SocketDefinitionParser());
    }
}
