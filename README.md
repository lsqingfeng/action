# 项目简介
springboot脚手架，目前已集成 mybatis-plus, quartz, redis-cluster，nocas

springboot版本： 2.2.5

项目结构：
    -- pom.xml
    -- action-parent : 所有子module的继承module, 用于maven依赖管理
    -- action-common:  common模块，主要放置所有子模块都能依赖到的公共组件，如枚举，常量，异常，工具类
    -- action-core: 依赖common, 核心基础模块，主要存放mybatis-plus自动生成的实体，mapper,service
    -- action-biz: 依赖core, 业务层，用于实现业务逻辑
    -- action-web: web应用层，依赖biz,主要写controller，
    -- action-generator: 不依赖任何模块，用于生成mybatis-plus的实体，mapper等，方便使用
    -- action-knowledge: 主要用于学习一些知识点，如设计模式，多线程等用法，目前暂未开始更新
    -- action.sql :  项目中用到的数据库执行语句
 
   自动配置： 如redis的配置，datasource的配置，都放到了core下的config包中，方便其他模块可以依赖到
   主配置文件位于：web下的resouces/application.yml中
   一些简单的演示都在web下的controller中，如CacheController演示redis缓存，QuartzController演示定时任务等
   
分支简介： 
    master:  springboot脚手架
    action-dubbo:  springboot整合dubbo脚手架
    action-springcloud:  springcloud脚手架
   





