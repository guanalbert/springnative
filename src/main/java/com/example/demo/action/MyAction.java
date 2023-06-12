package com.example.demo.action;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyAction {
    public MyAction(){
        log.info("Invoking public MyAction()");
    }
    public void doSomeAction(){
        log.info("Your are invoking Myaction.doSomeAction()");
    }
    public void doAnotherAction(){
        log.info("Your are invoking Myaction.doAnotherAction()");
    }
}
