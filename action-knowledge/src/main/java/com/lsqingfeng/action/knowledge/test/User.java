package com.lsqingfeng.action.knowledge.test;

import lombok.Builder;
import lombok.Data;

/**
 * @className: User
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-07 10:06
 */
@Data
@Builder
public class User {

    private String name;

    private int age;

    private String gener;


    public static void main(String[] args) {
        User build = User.builder().age(10).gener("男").name("张三").build();
        System.out.println(build);
    }


}
