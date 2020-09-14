package com.simple.general.annotation;

import java.lang.annotation.*;

/**
 * PassToken
 *
 * @author Mr.Wu
 * @date 2020/9/9 23:54
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassToken {
    boolean required() default true;
}
