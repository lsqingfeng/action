package com.lsqingfeng.action.web.controller;

import com.lsqingfeng.action.core.entity.ManUser;
import com.lsqingfeng.action.core.service.ManUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {

    public final ManUserService manUserService;

    public TestController(ManUserService manUserService) {
        this.manUserService = manUserService;
    }

    @RequestMapping("/test")
    public String test(){
        ManUser user = new ManUser();
        user.setName("zhangsan");
        user.setCreateTime(LocalDateTime.now());
        user.setCreateUser("system");
        user.setPhone("18888888888");
        user.setPassport("123456");
        user.setPasswordHash("123456");
        user.setPasswordSalt("abcd");
        manUserService.save(user);
        return "test";
    }
}
