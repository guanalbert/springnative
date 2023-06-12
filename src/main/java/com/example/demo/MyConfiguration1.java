package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.bean.ExampleBean1;

import lombok.extern.slf4j.Slf4j;

@Configuration(proxyBeanMethods  = true)
@Slf4j
public class MyConfiguration1 {
    @Bean
    public ExampleBean1 bean1() {
        log.info("Get bean1");
        return new ExampleBean1();
    }
}
