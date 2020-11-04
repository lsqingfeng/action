package com.lsqingfeng.action;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ActionShardingApplication
 * @description: sharding启动类
 * @author: sh.Liu
 * @date: 2020-08-04 10:27
 */
@SpringBootApplication(exclude = {SpringBootConfiguration.class})
public class ActionShardingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActionShardingApplication.class,args);
    }
}
