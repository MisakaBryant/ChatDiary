package com.mobileSE.chatdiary.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    // 单位时间：秒
    long seconds() default 1;

    // 单位时间内限制访问次数
    int count() default 10;

}

