package com.lsqingfeng.action.sharding.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ShardingBizApplication
 * @description:
 * @author: sh.Liu
 * @date: 2020-11-04 13:48
 */
@SpringBootApplication
@MapperScan(basePackages = {
        "com.lsqingfeng.action.sharding.biz.mapper"})
public class ShardingBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingBizApplication.class,args);
    }
}
