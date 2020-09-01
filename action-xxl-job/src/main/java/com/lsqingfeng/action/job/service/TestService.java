package com.lsqingfeng.action.job.service;

import com.lsqingfeng.action.core.entity.ManUser;
import com.lsqingfeng.action.core.service.ManUserService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @className: TestService
 * @description:
 * @author: sh.Liu
 * @create: 2020-04-23 13:27
 */
@Component
public class TestService {

    @Autowired
    private ManUserService manUserService;

    @XxlJob("myHandler")
    public ReturnT<String> execute(String param) {
        XxlJobLogger.log("hello world.");
        System.out.println(param);
        System.out.println("hahahahahaha");

        return ReturnT.SUCCESS;
    }

    public void test(){
        for(int i=0; i<10; i++){
            ManUser user = new ManUser();
            user.setName("xxl"+i);
            user.setPassport("abc"+i);
            user.setPasswordSalt("hahah"+i);
            user.setPasswordHash("hash"+i);
            user.setState("10");
            user.setCreateUser("xxjob"+i);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            manUserService.save(user);
        }
    }
}
