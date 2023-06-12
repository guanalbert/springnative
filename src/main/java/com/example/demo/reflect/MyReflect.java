package com.example.demo.reflect;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyReflect {
    private String context;
    public String myField = "I am a field of MyReflect.class";
    public MyReflect() {
    }
    public void setContext(String context) {
        this.context = context;
        log.info("Setting context:{}", context);
    }
    public String getContext(){
        log.info("Getting context:{}", context);
        return this.context;
    }
}
