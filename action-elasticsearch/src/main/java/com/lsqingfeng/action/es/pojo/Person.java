package com.lsqingfeng.action.es.pojo;

import lombok.Data;


import java.io.Serializable;

/**
 * @className: Person
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-22 13:35
 */
@Data
public class Person implements Serializable {

    private int id;

    private String name;

    private int age;

    private String desc;
}
