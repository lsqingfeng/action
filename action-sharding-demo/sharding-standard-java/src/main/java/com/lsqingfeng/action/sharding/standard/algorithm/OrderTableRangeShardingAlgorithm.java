package com.lsqingfeng.action.sharding.standard.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @className: OrderTableRangeShardingAlgorithm
 * @description: 范围分库分表
 * @author: sh.Liu
 * @date: 2020-11-05 19:07
 */
public class OrderTableRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {

        Collection<String> availables = new ArrayList<>();

        // 获取between and 边界值
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        Long lowValue = valueRange.lowerEndpoint();
        Long upperValue = valueRange.upperEndpoint();
        int i = 0;
        for (String targetTable : collection) {

            availables.add(targetTable);
        }
        return availables;
    }
}
