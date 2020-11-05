package com.lsqingfeng.action.sharding.inline.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnBean(value=DataSource.class)
@MapperScan(basePackages = {
        "com.lsqingfeng.action.sharding.biz.mapper"}
)
public class MybatisPlusConfig {

    @Autowired
    @Qualifier("shardingDataSource")
    private DataSource dataSource;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(-1);
        return paginationInterceptor;
    }

//    @Bean("sqlSessionFactoryForShardingjdbc")
//    public SqlSessionFactory sqlSessionFactoryForShardingjdbc() throws Exception {
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
////        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
////                getResources("classpath*:**/*.xml"));
////        sessionFactory.setTypeAliasesPackage("com.lsqingfeng.action.sharding.biz.entity");
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        sessionFactory.setConfiguration(configuration);
//        return sessionFactory.getObject();
//    }
}
