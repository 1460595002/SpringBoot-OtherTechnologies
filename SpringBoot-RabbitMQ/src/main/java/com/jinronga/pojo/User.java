package com.jinronga.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String name;
    private Integer id;
    private int age;
    private Integer gender;

}
