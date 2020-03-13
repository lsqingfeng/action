package com.lsqingfeng.action.web.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobContainer implements Job {
    /**
     * 实际执行的Job
     */
    private BaseJob baseJob;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        baseJob.execute(context);
    }

    public BaseJob getBaseJob() {
        return baseJob;
    }

    /**
     * 用于注入实际的任务
     *
     * @param baseJob 实际任务
     */
    public void setBaseJob(BaseJob baseJob) {
        this.baseJob = baseJob;
    }
}
