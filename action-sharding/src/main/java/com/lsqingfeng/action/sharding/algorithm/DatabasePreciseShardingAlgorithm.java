package com.lsqingfeng.action.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @className: DatabasePreciseShardingAlgorithm
 * @description: 数据库分片算法
 * @author: sh.Liu
 * @date: 2020-09-08 16:03
 */
@Component
public class DatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        Long curValue = preciseShardingValue.getValue();
        String curBase = "";
        if (curValue % 2 == 0) {
            curBase =  "ds0";
        }else{
            curBase = "ds1";
        }
        return curBase;
    }

}
