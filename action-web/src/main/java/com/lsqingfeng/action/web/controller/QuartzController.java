package com.lsqingfeng.action.web.controller;

import com.lsqingfeng.action.core.entity.QuartzInfo;
import com.lsqingfeng.action.core.service.QuartzInfoService;
import com.lsqingfeng.action.web.quartz.QuartzManageCenter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
@Slf4j
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    private QuartzManageCenter quartzManageCenter;

    @Autowired
    private QuartzInfoService quartzInfoService;

    @RequestMapping("/add")
    public String add(){
        log.info("添加任务");
        QuartzInfo info = new QuartzInfo();
        info.setCode("myTestTask2");
        info.setCreateTime(LocalDateTime.now());
        info.setCreateUser("system");
        info.setState("10");
        info.setUpdateUser("123");
        info.setName("测试任务2");
        info.setCycle("0/2 * * * * ?");
        quartzInfoService.save(info);
        quartzManageCenter.addJob(info);
        return "success";
    }

    @RequestMapping("/runOnce")
    public String runOnce() throws SchedulerException {
        log.info("执行一次");
        QuartzInfo info = quartzInfoService.getById(1);
        quartzManageCenter.runOnce(info);
        return "success";
    }

}
