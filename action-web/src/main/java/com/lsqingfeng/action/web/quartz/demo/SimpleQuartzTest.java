package com.lsqingfeng.action.web.quartz.demo;

import com.lsqingfeng.action.core.entity.ManUser;
import com.lsqingfeng.action.core.service.ManUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个简单的定时任务演示： 每5秒保存一条数据
 * 步骤：
 *      1， 引入定时任务依赖：  quarz-starter
 *      2.  开启定时任务： 在主启动类上加注解： @EnableScheduling
 *      3.  写一个类，加上注解，确保其能够被扫描到，通过Scheduled注解配置对应的cron表达式
 */
//@Component
@Slf4j
public class SimpleQuartzTest {

    @Autowired
    private ManUserService manUserService;

    AtomicInteger count = new AtomicInteger(1);

    @Scheduled(cron = "0/5 * * * * ? ")
    public void test(){
        ManUser user = new ManUser();
        log.info("定时任务开始执行");
        user.setName("zhangsan"+count.get());
        user.setCreateTime(LocalDateTime.now());
        user.setCreateUser("system"+count.get());
        user.setPhone("1888888888"+count.get());
        user.setPassport("123456"+count.get());
        user.setPasswordHash("123456"+count.get());
        user.setPasswordSalt("abcd"+count.get());
        manUserService.save(user);
        count.incrementAndGet();
    }
}
