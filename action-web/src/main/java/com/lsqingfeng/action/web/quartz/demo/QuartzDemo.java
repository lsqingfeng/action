package com.lsqingfeng.action.web.quartz.demo;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时任务的demo,主要用来 演示 quarz中的常用组件
 * quarz中的三要素
 * 1.  scheduler: 调度器，所有的调度都由其控制
 * 2.  trigger: 触发器，决定什么时候执行定时任务
 * 3.  job&detailjob: detailJob定义的是任务数据，而真正的 执行逻辑在job中
 *    使用二者组合，主要是为了解决并发问题。
 */
public class QuartzDemo {

    public static void main(String[] args) {
        try {
            // 获取Scheduler
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 启动scheduler
            scheduler.start();
            // 创建HelloworldJob的JobDetail实例，并设置name/group
            JobDetail jobDetail = JobBuilder.newJob(HelloworldJob.class)
                    .withIdentity("myJob","myJobGroup1")
                    //JobDataMap可以给任务传递参数
                    .usingJobData("job_param","job_param1")
                    .build();
            // 创建Trigger触发器设置使用cronSchedule方式调度
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger","myTriggerGroup1")
                    .usingJobData("job_trigger_param","job_trigger_param1")
                    .startNow()
                    //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? "))
                    .build();
            // 注册JobDetail实例到scheduler以及使用对应的Trigger触发时机
            scheduler.scheduleJob(jobDetail,trigger);


        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
