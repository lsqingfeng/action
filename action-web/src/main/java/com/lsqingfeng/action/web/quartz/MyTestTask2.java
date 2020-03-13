package com.lsqingfeng.action.web.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MyTestTask2 extends BaseJob {
    @Override
    public void handle() {
        log.info("mytask2...start...{}", LocalDateTime.now());
    }
}
