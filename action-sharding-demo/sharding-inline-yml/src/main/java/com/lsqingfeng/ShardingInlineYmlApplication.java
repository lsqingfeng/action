package com.lsqingfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ShardingInlineYmlApplication
 * @description:
 * @author: sh.Liu
 * @date: 2020-11-03 10:29
 */
@SpringBootApplication
@MapperScan({"com.lsqingfeng.action.sharding.biz.mapper"})
public class ShardingInlineYmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingInlineYmlApplication.class,args);
    }
}
