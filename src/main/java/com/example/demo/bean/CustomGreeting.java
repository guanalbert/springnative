package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomGreeting<T>{
    private long id;
    private T content;
 }