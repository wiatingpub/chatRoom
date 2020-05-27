package com.nuofankj.socket.schema;

import com.nuofankj.socket.anno.Api;
import com.nuofankj.socket.protocol.Packet;
import com.nuofankj.socket.server.MessageBean;
import com.nuofankj.socket.server.SocketServer;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author xifanxiaxue
 * @date 2019/04/10
 * @desc
 */
public class ApiInjectProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    private final InternalLogger log = InternalLoggerFactory.getInstance(ApiInjectProcessor.class);

    @Autowired
    private SocketServer socketServer;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        ReflectionUtils.doWithLocalMethods(bean.getClass(), new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                Api anno = method.getAnnotation(Api.class);
                if (anno == null) {
                    return;
                }

                for (Parameter parameter : method.getParameters()) {
                    Class<?> parmClass = parameter.getType();
                    if (Packet.class.isAssignableFrom(parameter.getType())) {
                        Packet pakcet = null;
                        try {
                            pakcet = (Packet) ReflectionUtils.accessibleConstructor(parmClass).newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        int commandId = pakcet.getCommand();
                        MessageBean messageBean = MessageBean.valueOf(bean, method, (Class<Packet>) parmClass);
                        if (socketServer.getMessageDispatcher().getCommandMap().putIfAbsent(commandId, messageBean) != null) {
                            log.error("协议包[id-{}]重复", pakcet.getCommand());
                            throw new IllegalStateException("协议包重复");
                        }
                        log.info("协议包id[{}]注册成功", commandId);
                    }
                }
            }
        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }
}
