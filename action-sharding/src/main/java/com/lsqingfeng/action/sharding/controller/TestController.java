package com.lsqingfeng.action.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lsqingfeng.action.sharding.entity.Order;
import com.lsqingfeng.action.sharding.entity.User;
import com.lsqingfeng.action.sharding.service.AddressService;
import com.lsqingfeng.action.sharding.service.OrderItemService;
import com.lsqingfeng.action.sharding.service.OrderService;
import com.lsqingfeng.action.sharding.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: TestController
 * @description:
 * @author: sh.Liu
 * @date: 2020-09-03 15:33
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;

    public TestController(UserService userService, OrderService orderService, OrderItemService orderItemService, AddressService addressService) {
        this.userService = userService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.addressService = addressService;
    }


    @RequestMapping("/user")
    public String testUser(){
        for (int i=1;i<=10; i++) {
            User user = new User();
            user.setUserId(Long.valueOf(i));
            user.setUserName("张三" + i);
            userService.save(user);
        }
        return "sucess";
    }

    @RequestMapping("/order")
    public String testOrder(){
        for (int i=1;i<=10; i++) {
            Order order = new Order();
            order.setUserId(Long.valueOf(i));
            order.setOrderCode("acb"+ i);
            order.setAddressId(String.valueOf(i));
            orderService.save(order);
        }
        return "sucess";
    }

    @RequestMapping("/listOrder")
    public Object testListOrder(){
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().orderByAsc(Order::getUserId));
        return list;
    }

    @RequestMapping("/update")
    public String updateOrder(){
        Order order = orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderId, 510462094471573504L));
        orderService.update(new LambdaUpdateWrapper<Order>().set(Order::getAddressId, "99999").eq(Order::getOrderId, 510462094471573504L));
        return "sucess";
    }



}
