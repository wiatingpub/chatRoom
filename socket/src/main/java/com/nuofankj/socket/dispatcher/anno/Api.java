package com.nuofankj.socket.dispatcher.anno;

import java.lang.annotation.*;

/**
 * @author xifanxiaxue
 * @date 2020/5/31 16:09
 * @desc api注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Api {
}
