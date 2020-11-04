package com.lsqingfeng.action.nacos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsqingfeng.action.nacos.entity.EventDO;
import com.lsqingfeng.action.nacos.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestController
 * @description: 测试类
 * @author: sh.Liu
 * @date: 2020-10-26 10:19
 */
@RestController
@RequestMapping("/nacos")
public class TestController {

    @Autowired
    private EventMapper eventMapper;

    @RequestMapping("test")
    public Object test () {
        EventDO eventDO = eventMapper.selectOne(new LambdaQueryWrapper<EventDO>().eq(EventDO::getEventCode, "202010190002"));
        return eventDO;
    }


}
