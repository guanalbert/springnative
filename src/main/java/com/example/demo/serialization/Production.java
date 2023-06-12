package com.example.demo.serialization;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Production implements Serializable {
    private int id;
    private Double price;
    private String name;
    private String description;
}
