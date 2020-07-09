package com.lsqingfeng.action;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: EsApplication
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-22 10:11
 */
@SpringBootApplication
@MapperScan({"com.lsqingfeng.action.**.dao", "com.lsqingfeng.action.**.mapper"})
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class,args);
    }
}
