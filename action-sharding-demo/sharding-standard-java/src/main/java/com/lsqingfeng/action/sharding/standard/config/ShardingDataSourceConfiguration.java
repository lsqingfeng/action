package com.lsqingfeng.action.sharding.standard.config;

import com.lsqingfeng.action.sharding.standard.algorithm.DatabasePreciseShardingAlgorithm;
import com.lsqingfeng.action.sharding.standard.algorithm.DatabaseRangeShardingAlgorithm;
import com.lsqingfeng.action.sharding.standard.algorithm.OrderTablePreciseShardingAlgorithm;
import com.lsqingfeng.action.sharding.standard.algorithm.OrderTableRangeShardingAlgorithm;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @className: ShardingDataSourceConfiguration
 * @description:
 * @author: sh.Liu
 * @date: 2020-11-03 19:23
 */
@Configuration
@MapperScan({"com.lsqingfeng.action.sharding.biz.mapper"})
public class ShardingDataSourceConfiguration {

    @Bean("shardingDataSource")
    public DataSource getShardingDataSource() throws SQLException, ReflectiveOperationException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 广播表
        List<String> broadcastTables = new LinkedList<>();
        broadcastTables.add("t_user");
        broadcastTables.add("t_address");
        shardingRuleConfig.setBroadcastTables(broadcastTables);

        // 默认策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds0"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "t_order_${user_id % 4 + 1}"));

        // 获取user表的分片规则配置
        TableRuleConfiguration standardRuleConfig = getTableRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(standardRuleConfig);
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }

    /**
     * 配置真实数据源
     * @return 数据源map
     */
    private Map<String, DataSource> createDataSourceMap() throws ReflectiveOperationException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        Map<String, Object> dataSourceProperties = new HashMap<>();
        dataSourceProperties.put("DriverClassName", "com.mysql.jdbc.Driver");
        dataSourceProperties.put("jdbcUrl", "jdbc:mysql://localhost:3306/ds0?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSourceProperties.put("username", "root");
        dataSourceProperties.put("password", "root");

        DataSource dataSource1 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties);

        Map<String, Object> dataSourceProperties2 = new HashMap<>();
        dataSourceProperties2.put("DriverClassName", "com.mysql.jdbc.Driver");
        dataSourceProperties2.put("jdbcUrl", "jdbc:mysql://localhost:3306/ds1?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dataSourceProperties2.put("username", "root");
        dataSourceProperties2.put("password", "root");

        DataSource dataSource2 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2);

        dataSourceMap.put("ds0",dataSource1);
        dataSourceMap.put("ds1",dataSource2);
        return dataSourceMap;
    }

    /**
     * 配置user表的分片规则
     *
     * @return ser表的分片规则配置对象
     */
    private TableRuleConfiguration getTableRuleConfiguration() {
        // 为user表配置数据节点
        TableRuleConfiguration ruleConfiguration = new TableRuleConfiguration("t_order", "ds${0..1}.t_order_$->{1..4}");

        // 若没有between and 可以没有最后的范围查询策略对象
        StandardShardingStrategyConfiguration dbShardingStrategyConfiguration = new StandardShardingStrategyConfiguration("user_id", new DatabasePreciseShardingAlgorithm(), new DatabaseRangeShardingAlgorithm());
        StandardShardingStrategyConfiguration tableShardingStrategyConfiguration = new StandardShardingStrategyConfiguration("user_id", new OrderTablePreciseShardingAlgorithm(),new OrderTableRangeShardingAlgorithm());

        // 设置自定义分库策略
        ruleConfiguration.setDatabaseShardingStrategyConfig(dbShardingStrategyConfiguration);

        // 设置自定义分表策略
        ruleConfiguration.setTableShardingStrategyConfig(tableShardingStrategyConfiguration);

        ruleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration("SNOWFLAKE", "order_id"));
        return ruleConfiguration;
    }








}
