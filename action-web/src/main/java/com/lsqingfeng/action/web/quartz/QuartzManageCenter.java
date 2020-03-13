package com.lsqingfeng.action.web.quartz;

import com.lsqingfeng.action.core.entity.QuartzInfo;
import com.lsqingfeng.action.web.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务管理中心
 */
@Component
@Slf4j
public class QuartzManageCenter {

    private static Scheduler scheduler = null;

    private static final String TASK = "TASK_";

    private static final String GROUP = "GROUP";

    static{
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJob(QuartzInfo quartzInfo){
        // 构建jobDetail
        JobDataMap jobDataMap = new JobDataMap();
        // 此处，通过map赋值，Job中有对应set方法即可把值注入进去
        jobDataMap.put("baseJob", SpringUtil.getBean(quartzInfo.getCode()));
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(JobContainer.class)
                .usingJobData(jobDataMap)
                .withIdentity(TASK+quartzInfo.getCode(),GROUP).build();

        // 2 构建trigger
        //通过触发器名和cron 表达式创建 Trigger
        Trigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TASK + quartzInfo.getId())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzInfo.getCycle()))
                .build();
        // 3.
        //重置启动时间
        ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

        //执行定时任务
        try {
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行一次
     * @throws SchedulerException
     */
    public void runOnce(QuartzInfo quartzInfo) throws SchedulerException {
        log.info("任务调度中心,执行一次定时任务:{}", quartzInfo);
        scheduler.triggerJob(JobKey.jobKey(TASK+quartzInfo.getCode(),GROUP));
    }

    /**
     * 停止调度中心
     */
    public void shutdown() throws SchedulerException {
        log.info("任务调度中心,停止调度中心");
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    /**
     * 删除任务
     */
    public void deleteJob(QuartzInfo quartzInfo) throws SchedulerException {
        log.info("任务调度中心,删除定时任务:{}", quartzInfo);
        scheduler.deleteJob(JobKey.jobKey(TASK+quartzInfo.getCode(),GROUP));
    }

}
