package com.lsqingfeng.action.web.quartz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsqingfeng.action.core.entity.QuartzInfo;
import com.lsqingfeng.action.core.service.QuartzInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用于初始化定时任务，从数据库中加载所需执行的定时任务
 * 程序启动时执行
 */
@Component
@Slf4j
public class InitQuartzRunner implements CommandLineRunner {

    @Autowired
    private QuartzInfoService quartzInfoService;

    @Autowired
    private QuartzManageCenter quartzManageCenter;

    @Override
    public void run(String... args) throws Exception {
        log.info("定时器注入开始。。。。");
        List<QuartzInfo> list = quartzInfoService.list(new LambdaQueryWrapper<QuartzInfo>().eq(QuartzInfo::getState,"10"));
        if(list != null && list.size() >0){
            for(QuartzInfo info: list){
                quartzManageCenter.addJob(info);
                log.info("任务{} ,注入成功",info.getCode());
            }
        }
    }
}
