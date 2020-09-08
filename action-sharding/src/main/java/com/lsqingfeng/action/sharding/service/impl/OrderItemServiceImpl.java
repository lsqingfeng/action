package com.lsqingfeng.action.sharding.service.impl;

import com.lsqingfeng.action.sharding.entity.OrderItem;
import com.lsqingfeng.action.sharding.mapper.OrderItemMapper;
import com.lsqingfeng.action.sharding.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
