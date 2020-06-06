package com.nuofankj.socket.dispatcher;

import com.nuofankj.socket.dispatcher.anno.Api;
import com.nuofankj.socket.dispatcher.bean.MessageBean;
import com.nuofankj.socket.proto.AbstractMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

/**
 * @author xifanxiaxue
 * @date 2019/04/10
 * @desc api注入
 */
@Slf4j
@Component
public class ApiInjectProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithLocalMethods(bean.getClass(), method -> {
            Api annotation = method.getAnnotation(Api.class);
            if (annotation == null) {
                return;
            }

            for (Parameter parameter : method.getParameters()) {
                Class<?> paramClass = parameter.getType();
                if (AbstractMessage.class.isAssignableFrom(parameter.getType())) {
                    AbstractMessage abstractMessage = null;
                    try {
                        abstractMessage = (AbstractMessage) ReflectionUtils.accessibleConstructor(paramClass).newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    int messageId = abstractMessage.getCode();
                    MessageBean messageBean = MessageBean.valueOf(bean, method, (Class<AbstractMessage>) paramClass);
                    MessageBean originMessageBean = MessageDispatcher.id2MessageBeanMap.putIfAbsent(messageId, messageBean);
                    if (originMessageBean != null) {
                        log.error("协议id:{}重复", messageId);
                        throw new IllegalStateException("协议id重复");
                    }
                    log.info("协议id：{}录入成功", messageId);
                }
            }
        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }
}
