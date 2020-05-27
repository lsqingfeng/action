package com.lsqingfeng.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @className: JobApplication
 * @description:
 * @author: sh.Liu
 * @create: 2020-04-23 13:26
 */
@SpringBootApplication
@EnableScheduling
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class,args);
    }

}
