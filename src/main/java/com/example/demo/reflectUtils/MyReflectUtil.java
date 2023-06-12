package com.example.demo.reflectUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ReflectionUtils;

import com.example.demo.bean.ClassIntrospectResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyReflectUtil {
    public static ClassIntrospectResult introspectFieldsAndMethodsFrom(String clzName){
        StringBuilder sb = new StringBuilder();
        ClassIntrospectResult rst = new ClassIntrospectResult();
        rst.setClassName(clzName);
        Class clz = null;
        try {
            clz = Class.forName(clzName);
        } catch (Exception e) {
            rst.setIntrospectResult(ClassIntrospectResult.INTROSPECT_RESULT_NG);
        }
        if (clz == null){
            rst.setIntrospectResult(ClassIntrospectResult.INTROSPECT_RESULT_NG);
        }else{
            sb.append("Fields and methods in ").append(clz.getCanonicalName()).append(":\\n");
            try {
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    rst.getFields().add(field.getName());
                }
            } catch (Exception e) {
                log.warn("Cannot get the declared fields", e);
            }
            try {
                Method[] methods = ReflectionUtils.getDeclaredMethods(clz);
                sb.append("\\tMethods:\\n");
                for (Method method : methods) {
                    rst.getMethods().add(method.getName());
                }
            } catch (Exception e) {
                sb.append("Cannot get the declared methods\\n");
                log.warn("Cannot get the declared methods", e);
            }
            Constructor[] cons = clz.getDeclaredConstructors();
            if (cons != null) {
                if (rst.getConstructors() == null) {
                    rst.setConstructors(new ArrayList<>());
                }
                for (int i = 0; i < cons.length; i++) {
                    Class[] types = cons[i].getParameterTypes();
                    if (types.length > 0) {
                        String params = Arrays.stream(types).map((c)-> c.getName()).collect(Collectors.joining(","));
                        rst.getConstructors().add(clz.getSimpleName()+"("+params+")");
                    }else{
                        rst.getConstructors().add(clz.getSimpleName()+"()");
                    }
                }
            }
        }
        return rst;
    }
}
