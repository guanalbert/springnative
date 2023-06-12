package com.example.demo.hints;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import com.example.demo.action.MyAction;
import com.example.demo.aspect.MyAspect;
import com.example.demo.proxy.GreetingMethods;
import com.example.demo.proxy.MyGreeting;
import com.example.demo.reflect.MyReflect;
import com.example.demo.serialization.Production;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyRuntimeHint implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
        log.info("####################Registing runtime hints: " );
        //hints.reflection().registerType(MyContent.class);
        hints.reflection().registerType(MyAspect.class,MemberCategory.INVOKE_DECLARED_METHODS);
        hints.reflection().registerType(MyReflect.class,MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
        

       Method actionMethod = ReflectionUtils.findMethod(MyAction.class, "doSomeAction");
        hints.reflection().registerMethod(actionMethod, ExecutableMode.INTROSPECT);

        Method method = ReflectionUtils.findMethod(MyReflect.class, "getContext");
        hints.reflection().registerMethod(method, ExecutableMode.INVOKE);

        hints.reflection().registerType(TypeReference.of("com.example.demo.bean.InnerBeanA"), MemberCategory.PUBLIC_FIELDS);
        hints.reflection().registerType(TypeReference.of("com.example.demo.bean.InnerBeanB"), MemberCategory.INTROSPECT_DECLARED_METHODS);
        try {
            Class clz = Class.forName("com.example.demo.bean.InnerBeanC");
            Field field1 = ReflectionUtils.findField(clz, "field1");
            hints.reflection().registerField(field1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        hints.reflection().registerType(com.example.demo.bean.InnerBeanD.class);


        Method method1 = ReflectionUtils.findMethod(gx.spring.mybeans.BeanA.class, "method1");
        hints.reflection().registerMethod(method1, ExecutableMode.INTROSPECT);
        try {
            hints.reflection().registerConstructor(gx.spring.mybeans.BeanA.class.getDeclaredConstructor(), ExecutableMode.INTROSPECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        method1 = ReflectionUtils.findMethod(gx.spring.mybeans.BeanB.class, "method1");
        try {
            hints.reflection().registerConstructor(gx.spring.mybeans.BeanB.class.getDeclaredConstructor(), ExecutableMode.INVOKE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hints.reflection().registerMethod(method1, ExecutableMode.INVOKE);

        hints.reflection().registerType(gx.spring.mybeans.BeanC.class,MemberCategory.INVOKE_DECLARED_METHODS);

        hints.reflection().registerType(gx.spring.mybeans.BeanD.class,MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

        ////////////////////////////////Proxy registrations////////////////////////////////
        hints.proxies().registerJdkProxy(GreetingMethods.class);
        ////////////////////////////////Serialization////////////////////////////////
        hints.serialization().registerType(TypeReference.of(Production.class));
        hints.serialization().registerType(TypeReference.of(String.class));
        hints.serialization().registerType(TypeReference.of(Double.class));
        hints.serialization().registerType(TypeReference.of(Integer.class));
        hints.serialization().registerType(TypeReference.of(Number.class));
        ////////////////////////////////Resources////////////////////////////////
        hints.resources().registerPattern("resource1.txt");
    }
    
}
