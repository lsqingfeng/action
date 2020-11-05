package com.lsqingfeng.action.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ShardingInlineYmlApplication
 * @description: 采用yml配置的方式采用Standard策略分库分表
 * @author: sh.Liu
 * @date: 2020-11-03 10:29
 */
@SpringBootApplication
@MapperScan({"com.lsqingfeng.action.sharding.biz.mapper"})
public class ShardingStandardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingStandardApplication.class,args);
    }
}
