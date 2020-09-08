package com.lsqingfeng.action.sharding.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsqingfeng.action.sharding.entity.Order;
import com.lsqingfeng.action.sharding.mapper.OrderMapper;
import com.lsqingfeng.action.sharding.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liushuai
 * @since 2020-09-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
