package com.lsqingfeng.action.docker.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: DockerTestController
 * @description:
 * @author: sh.Liu
 * @date: 2020-08-28 09:46
 */
@RestController
@RequestMapping("/docker")
public class DockerTestController {

    @RequestMapping("/test")
    public String test(){
        return "hello Docker!!!!";
    }

}
