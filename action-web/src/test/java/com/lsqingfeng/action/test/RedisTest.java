package com.lsqingfeng.action.test;

import com.lsqingfeng.action.WebApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
@SpringBootApplication
@SpringBootTest(classes = WebApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        stringRedisTemplate.opsForValue().set("Name", "Susan King");
        stringRedisTemplate.opsForValue().set("Age", "29");
        System.out.println(stringRedisTemplate.opsForValue().get("Name"));
        System.out.println(stringRedisTemplate.opsForValue().get("age"));
    }




}





