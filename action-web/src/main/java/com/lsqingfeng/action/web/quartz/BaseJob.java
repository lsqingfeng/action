package com.lsqingfeng.action.web.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
@Slf4j
public abstract class BaseJob {

    public void execute(JobExecutionContext context) {
        log.info("{},开始执行 ... ... ",context.getJobDetail().getJobDataMap().get("baseJob"));
        handle();
    }

    public abstract void handle();
}
