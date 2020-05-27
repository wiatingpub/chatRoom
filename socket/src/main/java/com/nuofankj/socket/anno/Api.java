package com.nuofankj.socket.anno;

import java.lang.annotation.*;

/**
 * @author xifanxiaxue
 * @date 5/23/19
 * @desc 接口注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Api {

    int commandId() default 0;
}
