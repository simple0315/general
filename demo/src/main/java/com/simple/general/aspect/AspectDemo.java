package com.simple.general.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring Aop
 *
 * @author wcy
 * @date 2020/9/9 16:28
 */
@Aspect
@Component
@Slf4j
public class AspectDemo {

    @Pointcut("execution(* com.simple.general.demo..*.*(..))")
    public void DemoTest() {

    }

    @Before("DemoTest()")
    public void LogRequestInfo(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        sb.append("请求信息：").append("URL = {").append(request.getRequestURI()).append("},\t")
                .append("请求方式 = {").append(request.getMethod()).append("},\t")
                .append("请求IP = {").append(request.getRemoteAddr()).append("},\t")
                .append("类方法 = {").append(signature.getDeclaringTypeName()).append(".")
                .append(signature.getName()).append("},\t");
        log.info(sb.toString());
    }

    @AfterReturning(pointcut = "DemoTest()")
    public void afterReturning() {
        log.info("afterReturning!!!");
    }

}
