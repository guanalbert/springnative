package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.ExampleBean2;

import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods  = false)
@Slf4j
public class MyConfiguration2 {
    @Bean
    public ExampleBean2 bean2() {
        log.info("Get bean2");
        return new ExampleBean2();
    }
}
