package com.lsqingfeng.action.sharding;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ShardingStandardApplication
 * @description: 采用java config配置的方式采用Standard策略分库分表
 * @author: sh.Liu
 * @date: 2020-11-03 10:29
 */
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
public class ShardingStandardJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingStandardJavaApplication.class,args);
    }
}
