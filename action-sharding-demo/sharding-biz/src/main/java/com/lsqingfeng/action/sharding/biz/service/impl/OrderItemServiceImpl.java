package com.lsqingfeng.action.sharding.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsqingfeng.action.sharding.biz.entity.OrderItem;
import com.lsqingfeng.action.sharding.biz.mapper.OrderItemMapper;
import com.lsqingfeng.action.sharding.biz.service.OrderItemService;
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
