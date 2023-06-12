package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicInvocationHandler implements InvocationHandler {

    private Object obj;

    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(
            obj.getClass().getClassLoader(),
            obj.getClass().getInterfaces(),
            new DynamicInvocationHandler(obj));
    }

    private DynamicInvocationHandler(Object obj) {
        this.obj = obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Invoking method {} from proxy",method.getName());
        return method.invoke(this.obj, args);
    }   
}
