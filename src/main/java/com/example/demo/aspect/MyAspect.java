package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.example.demo.annotation.AuditLog;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class MyAspect {
    @Before(value = "@annotation(com.example.demo.annotation.AuditLog)")
    public void before(JoinPoint  joinPoint){
        log.info("Invoking MyAspect.before()");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AuditLog logAnn =methodSignature.getMethod().getDeclaredAnnotation(AuditLog.class);
        String module = logAnn.module();
        log.info("Audit log, module = {}", module);
    }
}
