package com.lsqingfeng.action.sharding.standard.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lsqingfeng.action.sharding.biz.entity.Address;
import com.lsqingfeng.action.sharding.biz.entity.Order;
import com.lsqingfeng.action.sharding.biz.entity.User;
import com.lsqingfeng.action.sharding.biz.service.AddressService;
import com.lsqingfeng.action.sharding.biz.service.OrderItemService;
import com.lsqingfeng.action.sharding.biz.service.OrderService;
import com.lsqingfeng.action.sharding.biz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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
public class TestShardingStandardController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;

    public TestShardingStandardController(UserService userService, OrderService orderService, OrderItemService orderItemService, AddressService addressService) {
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
        for (int i=1;i<=100; i++) {
            Order order = new Order();
            order.setUserId(Long.valueOf(i));
            order.setOrderCode("acb"+ i);
            order.setAddressId(String.valueOf(i+100));
            orderService.save(order);
        }
        return "sucess";
    }

    @RequestMapping("/address")
    public String testAddress(){
        for (int i=11;i<=20; i++) {
            Address address = new Address();
            address.setAddressId(Long.valueOf(i));
            address.setAddressName("北京" + i);
            addressService.save(address);
        }
        return "sucess";
    }

    @RequestMapping("/listOrder")
    public Object testListOrder(){
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().orderByAsc(Order::getUserId));
        return list;
    }

    @RequestMapping("/listAddress")
    public Object testListAddress(){
        List<Address> list = addressService.list(new LambdaQueryWrapper<Address>().orderByAsc(Address::getAddressId));
        return list;
    }

    @RequestMapping("/update")
    public String updateOrder(){
        Order order = orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderId, 510462094471573504L));
        orderService.update(new LambdaUpdateWrapper<Order>().set(Order::getAddressId, "99999").eq(Order::getOrderId, 510462094471573504L));
        return "sucess";
    }

    @RequestMapping("/listOrder2")
    public Object testListOrder2(){
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().in(Order::getUserId, Arrays.asList(1L,2L,3L,4L,5L,6L,7L)).orderByAsc(Order::getUserId));
        return list;
    }

    // UnsupportedOperationException
    @RequestMapping("/listOrder3")
    public Object testListOrder3(){
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().between(Order::getAddressId, 100L, 127L).orderByAsc(Order::getUserId));
        return list;
    }

}
