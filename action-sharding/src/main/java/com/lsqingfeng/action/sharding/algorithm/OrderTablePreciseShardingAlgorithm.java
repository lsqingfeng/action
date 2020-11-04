package com.lsqingfeng.action.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @className: OrderTablePreciseShardingAlgorithm
 * @description:
 * @author: sh.Liu
 * @date: 2020-09-08 19:07
 */
@Component
public class OrderTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        System.out.println(collection);
        // 获取配置的分片字段 -- sharding-column
        Long curValue = preciseShardingValue.getValue();
        System.out.println(curValue.hashCode()  & Integer.MAX_VALUE);
        int i = ((curValue.hashCode() & Integer.MAX_VALUE )% 4 )+ 1;
        System.out.println(i);
//        String curTable = "";
//        if (curValue > 0 && curValue<=10) {
//            curTable = "t_order_1";
//        } else if (curValue > 10 && curValue<=20) {
//            curTable = "t_order_2";
//        } else if (curValue > 20 && curValue<=30) {
//            curTable = "t_order_3";
//        } else {
//            curTable = "t_order_4";
//        }
        return "t_order_" + i;
    }

    public static void main(String[] args) {
        System.out.println(546452176 % 4 + 1 );
    }
}
