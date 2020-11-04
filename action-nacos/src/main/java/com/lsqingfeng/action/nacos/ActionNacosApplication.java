package com.lsqingfeng.action.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @className: ActionNacosApplication
 * @description: action-nacos启动类
 * @author: sh.Liu
 * @date: 2020-10-26 10:16
 */
@SpringBootApplication
@MapperScan({"com.lsqingfeng.action.**.dao", "com.lsqingfeng.action.**.mapper"})
@NacosPropertySource(dataId = "action-nacos-config-example", autoRefreshed = true)
public class ActionNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActionNacosApplication.class, args);
    }

}
