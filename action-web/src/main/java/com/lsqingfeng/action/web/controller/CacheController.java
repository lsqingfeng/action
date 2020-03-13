package com.lsqingfeng.action.web.controller;

import com.lsqingfeng.action.biz.ManUserBiz;
import com.lsqingfeng.action.core.entity.ManUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  错误1：MOVED : 检查配置文件配置的是否是集群
 *  错误2：CLUSTERDOWN Hash slot not served
 *         使用命令修复redis集群：redis-cli --cluster fix 192.168.1.14:6379
 */
@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController {

    @Autowired
    private ManUserBiz manUserBiz;

    @RequestMapping("/get")
    public String getById(){
        ManUser user = manUserBiz.getById(20L);
        return user.toString();
    }




}
