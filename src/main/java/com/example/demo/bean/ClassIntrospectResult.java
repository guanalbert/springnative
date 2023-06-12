package com.example.demo.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClassIntrospectResult {
    public static final String INTROSPECT_RESULT_OK = "OK";
    public static final String INTROSPECT_RESULT_NG = "NG";
    private String className;
    private String introspectResult;
    private List<String> methods = new ArrayList<String>();
    private List<String> fields = new ArrayList<String>();
    private List<String> constructors = new ArrayList<String>();
}
