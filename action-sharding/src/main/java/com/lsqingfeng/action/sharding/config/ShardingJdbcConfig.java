package com.lsqingfeng.action.sharding.config;

import com.lsqingfeng.action.sharding.algorithm.DatabasePreciseShardingAlgorithm;
import com.lsqingfeng.action.sharding.algorithm.OrderTablePreciseShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @className: ShardingJdbcConfig
 * @description: java config 方式
 * @author: sh.Liu
 * @date: 2020-09-10 09:24
 */
@Configuration
public class ShardingJdbcConfig {

    @Resource
    private DatabasePreciseShardingAlgorithm databasePreciseShardingAlgorithm;

    @Resource
    private OrderTablePreciseShardingAlgorithm orderTablePreciseShardingAlgorithm;

    @Bean("ds0")
    public DataSource ds0() throws ReflectiveOperationException {
        Map<String, Object> dataSourceProperties = new HashMap<>();
        dataSourceProperties.put("DriverClassName", "com.mysql.jdbc.Driver");
        dataSourceProperties.put("jdbcUrl", "jdbc:mysql://localhost:3306/ds0?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSourceProperties.put("username", "root");
        dataSourceProperties.put("password", "root");

        DataSource ds0 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties);
        return ds0;
    }

    @Bean("ds1")
    public DataSource ds1() throws ReflectiveOperationException {
        Map<String, Object> dataSourceProperties = new HashMap<>();
        dataSourceProperties.put("DriverClassName", "com.mysql.jdbc.Driver");
        dataSourceProperties.put("jdbcUrl", "jdbc:mysql://localhost:3306/ds1?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSourceProperties.put("username", "root");
        dataSourceProperties.put("password", "root");

        DataSource ds1 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties);
        return ds1;
    }

    @Bean
    public DataSource dataSource(@Qualifier("ds0") DataSource ds0, @Qualifier("ds1") DataSource ds1) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", ds0);
        dataSourceMap.put("ds1", ds1);
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("dataSource");
        //如果有多个数据表需要分表，依次添加到这里
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        Properties p = new Properties();
        p.setProperty("sql.show", Boolean.TRUE.toString());
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,p);
        return dataSource;

    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    //订单表的分表规则配置
    private TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order","ds$->{0..1}.t_order_$->{1..4}");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id",databasePreciseShardingAlgorithm));
        result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id",orderTablePreciseShardingAlgorithm));
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    private KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
        return result;
    }

}
