package com.example.demo.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InnerBeanB implements InnerBean{
    public String field1 = "InnerBeanB_field1";
    public String filed2 = "InnerBeanB_filed2";

    public InnerBeanB(){}
    @Override
    public void printField1() {
        log.info("print value of field1:{}", field1);
    }

    @Override
    public void printField2() {
        log.info("print value of field2:{}", filed2);
    }

    @Override
    public void doNothing() {
        log.info("Invoking doNothing in InnerBeanB");
    }
}
