package com.nuofankj.socket.schema;

import com.nuofankj.socket.server.SocketServer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author xifanxiaxue
 * @date 2019/04/10
 * @desc
 */
public class SocketDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

        registerStaticInject(parserContext);

        String ip = null;
        String port = null;
        String type = null;

        NodeList child = element.getChildNodes();
        for (int i = 0; i < child.getLength(); i++) {

            Node node = child.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            String name = node.getLocalName();
            if (name.equals(SchemaNames.LOCATION_ELEMENT)) {
                Element e = (Element) node;
                ip = e.getAttribute(SchemaNames.LOCATION_SOCKET_IP);
                port = e.getAttribute(SchemaNames.LOCATION_SOCKET_PORT);
                type = e.getAttribute(SchemaNames.CONNECT_TYPE);
            }
        }

        BeanDefinitionBuilder factory = BeanDefinitionBuilder
                .rootBeanDefinition(SocketServer.class);
        factory.addPropertyValue("port", port);

        return factory.getBeanDefinition();
    }

    // 注册ApiInjectProcessorr
    private void registerStaticInject(ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        String name = StringUtils.uncapitalize(ApiInjectProcessor.class.getSimpleName());
        BeanDefinitionBuilder factory = BeanDefinitionBuilder
                .rootBeanDefinition(ApiInjectProcessor.class);
        registry.registerBeanDefinition(name, factory.getBeanDefinition());
    }
}