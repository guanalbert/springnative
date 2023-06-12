package com.example.demo.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InnerBeanD implements InnerBean{
    public String field1 = "InnerBeanD_field1";
    public String filed2 = "InnerBeanD_filed2";

    public InnerBeanD(){}
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
        log.info("Invoking doNothing in InnerBeanD");
    }
}
