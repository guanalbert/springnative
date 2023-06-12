package com.example.demo.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InnerBeanC implements InnerBean{
    public String field1 = "InnerBeanC_field1";
    public String filed2 = "InnerBeanC_filed2";

    public InnerBeanC(){}
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
        log.info("Invoking doNothing in InnerBeanC");
    }
}
