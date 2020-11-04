package com.lsqingfeng;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ShardingInlineYmlApplication
 * @description: 采用java config配置的方式采用inline方式分库分表
 * @author: sh.Liu
 * @date: 2020-11-03 10:29
 */
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
public class ShardingInlineJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingInlineJavaApplication.class,args);
    }
}
