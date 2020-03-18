package com.lsqingfeng.action.web.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 延迟队列消息消费：
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "delayTopic", consumerGroup = "delay_consumer",selectorExpression = "*")
public class DelayConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("延迟队列收到消息，时间：{}, 内容：{}", LocalDateTime.now(),message);
    }
}
