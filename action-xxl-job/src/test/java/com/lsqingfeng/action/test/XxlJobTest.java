package com.lsqingfeng.action.test;


import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import org.junit.Assert;
import org.junit.Test;

/**
 * @className: XxlJobTest
 * @description:
 * @author: sh.Liu
 * @date: 2020-08-27 11:12
 */
public class XxlJobTest {

    private static String addressUrl = "http://localhost:8654";

    private static String accessToken = "aaaaa";

    @Test
    public void run(){
        ExecutorBiz executorBiz = new ExecutorBizClient(addressUrl, accessToken);

        // trigger data
        final TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(1);
        triggerParam.setExecutorHandler("myHandler");
        triggerParam.setExecutorParams(null);
        triggerParam.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.COVER_EARLY.name());
        triggerParam.setGlueType(GlueTypeEnum.BEAN.name());
        triggerParam.setGlueSource(null);
        triggerParam.setGlueUpdatetime(System.currentTimeMillis());
        triggerParam.setLogId(1);
        triggerParam.setLogDateTime(System.currentTimeMillis());

        // Act
        final ReturnT<String> retval = executorBiz.run(triggerParam);
        System.out.println(retval);

        // Assert result
        Assert.assertNotNull(retval);
    }


}
