package com.lsqingfeng.action.job.controller;

import com.lsqingfeng.action.job.pojo.XxlJobInfo;
import com.lsqingfeng.action.job.util.XxlJobUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestController
 * @description:
 * @author: sh.Liu
 * @date: 2020-08-31 15:05
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final XxlJobUtil xxlJobUtil;

    public TestController(XxlJobUtil xxlJobUtil) {
        this.xxlJobUtil = xxlJobUtil;
    }


    /**
     * {
     *   "code": 200,
     *   "msg": null,
     *   "content": "5"
     * }
     * @return content 代表保存后的任务id, 需存储，后边会使用
     */
    @RequestMapping("/add")
    public String add() {
        XxlJobInfo jobInfo = new XxlJobInfo();
        // cron表达式：
        jobInfo.setJobCron("2 * * * * ?");
        // 执行模式： BEAN
        jobInfo.setGlueType("BEAN");
        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        // 类中标志有 myHandler的方法
        jobInfo.setExecutorHandler("myHandler");
        jobInfo.setAuthor("admin");
        jobInfo.setJobDesc("测试定时任务");
        jobInfo.setExecutorParam("我是参数");
        // 执行策略：FIRST,具体查看xxl-job官方文档
        jobInfo.setExecutorRouteStrategy("FIRST");
        return xxlJobUtil.add(jobInfo);
    }

    @RequestMapping("/update")
    public String update() {
        return xxlJobUtil.update(5, "1/2 * * * * ?");
    }

    @RequestMapping("/remove")
    public String remove() {
        return xxlJobUtil.remove(5);
    }

    @RequestMapping("/start")
    public String start() {
        return xxlJobUtil.start(5);
    }

    @RequestMapping("/pause")
    public String pause() {
        return xxlJobUtil.pause(5);
    }

    @RequestMapping("/addAndStart")
    public String addAndStart() {
        XxlJobInfo jobInfo = new XxlJobInfo();
        // cron表达式：
        jobInfo.setJobCron("3/1 * * * * ?");
        // 执行模式： BEAN
        jobInfo.setGlueType("BEAN");
        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        // 类中标志有 myHandler的方法
        jobInfo.setExecutorHandler("myHandler");
        jobInfo.setAuthor("admin");
        jobInfo.setJobDesc("测试定时任务2");
        jobInfo.setExecutorParam("我是参数");
        // 执行策略：FIRST,具体查看xxl-job官方文档
        jobInfo.setExecutorRouteStrategy("FIRST");

        return xxlJobUtil.addAndStart(jobInfo);
    }




}
