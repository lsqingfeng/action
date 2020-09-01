###分析研判系统集成支持Rest调用的xxl-job步骤：


#####1. 引入依赖
        <dependency>
            <groupId>com.xuxueli</groupId>
            <artifactId>xxl-job-core</artifactId>
            <version>2.2.0</version>
        </dependency>

#####2. yml加入配置：
    xxl:
      job:
        admin:
          addresses: http://192.168.200.39:9090/xxl-job-admin
        executor:
          appname: analysisJudge
          ip:
          port: 8654
          logpath: /data/applogs/xxl-job/jobhandler
          logretentiondays: 30
        ### xxl-job, access token
        ### 执行器通讯TOKEN [选填]：非空时启用；
        accessToken: cestc-xxl-job
        
##### 3. 引入配置类
将 com.lsqingfeng.action.job.config.XxlJobConfig 类拷入到系统中可以被spring扫描到的路径下

#####4. 引入一个实体类：
com.lsqingfeng.action.job.pojo.XxlJobInfo

#####5. 引入一个工具类
com.lsqingfeng.action.job.util.XxlJobUtil

#####6. 使用案例：
参照：com.lsqingfeng.action.job.controller.TestController
里面介绍了 使用rest方式调用定时任务的新增，启动，暂停，更新，删除，等操作

#####7. 特别注意：
上述案例中，xxl-job的执行模式使用的是BEAN, 也就定时任务的业务逻辑会执行你项目中的方法，这个方法要求你使用
@XxlJob("myHandler") 注释做标注，然后在创建任务时候，通过

        jobInfo.setGlueType("BEAN");
        // 类中标志有 myHandler的方法
        jobInfo.setExecutorHandler("myHandler");
 
 设置到任务中。   