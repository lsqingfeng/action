package com.lsqingfeng.action.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestController
 * @description:
 * @author: sh.Liu
 * @create: 2020-04-15 22:26
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "success";
    }
}
