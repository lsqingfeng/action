package com.lsqingfeng.action.web.rocketmq;

import com.lsqingfeng.action.web.rocketmq.domain.OrderPaidEvent;
import com.lsqingfeng.action.web.rocketmq.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *  消息生产者： 模拟演示生产不同类型的消息，查看对应消费者的状态
 */
@RestController
@RequestMapping("/mq")
@Slf4j
public class ProducerController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${demo.rocketmq.topic}")
    private String stringTopic;

    @Value("${demo.rocketmq.topic.user}")
    private String userTopic;

    @Value("${demo.rocketmq.orderTopic}")
    private String orderPaidTopic;

    @Value("${demo.rocketmq.msgExtTopic}")
    private String msgExtTopic;

    @Value("${demo.rocketmq.stringRequestTopic}")
    private String stringRequestTopic;

    @Value("${demo.rocketmq.bytesRequestTopic}")
    private String bytesRequestTopic;

    @RequestMapping("/sync/send1")
    public String syncSendString(){
        //发送一个同步 消息，会返回值 ---发送到 stringTopic主题
        SendResult sendResult = rocketMQTemplate.syncSend(stringTopic, "Hello, World!");
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", stringTopic, sendResult);
        //consumer result:------- StringConsumerNewNS received: Hello, World!
        return sendResult.toString();
    }
    // 使用同步模式发送到userTopic
    @RequestMapping("/sync/send2")
    public String syncSendUser(){
        //发送一个同步 消息，会返回值 ---发送到 userTopic
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, new User().setUserAge((byte) 18).setUserName("Kitty"));
        System.out.printf("syncSend2 to topic %s sendResult=%s %n", userTopic, sendResult);
        // consumer获取结果：user_consumer received: User{userName='Kitty', userAge=18} ; age: 18 ; name: Kitty
        return sendResult.toString();
    }

    // 使用同步模式发送到userTopic
    @RequestMapping("/sync/send3")
    public String syncSendUser2(){
        //使用MessageBuilder形式发送 ---发送到 userTopic
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, MessageBuilder.withPayload(
                new User().setUserAge((byte) 21).setUserName("Lester")).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        System.out.printf("syncSend1 to topic %s sendResult=%s %n", userTopic, sendResult);
        // consumer获取结果：user_consumer received: User{userName='Lester', userAge=21} ; age: 21 ; name: Lester
        return sendResult.toString();
    }

    // stringTopic
    @RequestMapping("/sync/send4")
    public String syncSendString2(){
        //使用MessageBuilder形式发送 ---发送到 stringTopic
        SendResult sendResult = rocketMQTemplate.syncSend(stringTopic, MessageBuilder.withPayload("Hello, World!2222".getBytes()).build());
        System.out.printf("extRocketMQTemplate.syncSend1 to topic %s sendResult=%s %n", stringTopic, sendResult);
        // consumer获取结果：------- StringConsumerNewNS received: Hello, World!2222
        return sendResult.toString();
    }

    // 异步消息 发送到orderPaidTopic
    @RequestMapping("/async/send1")
    public String asyncSend1(){
        // Send user-defined object
        rocketMQTemplate.asyncSend(orderPaidTopic, new OrderPaidEvent("T_001", new BigDecimal("88.00")), new SendCallback() {
            @Override
            public void onSuccess(SendResult var1) {
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            @Override
            public void onException(Throwable var1) {
                System.out.printf("async onException Throwable=%s %n", var1);
            }
            // consumer: ------- OrderPaidEventConsumer received: com.lsqingfeng.action.web.rocketmq.domain.OrderPaidEvent@4ebff5d2 [orderId : T_001]
        });
        return "success";
    }

    //special send : 带tag发送
    @RequestMapping("/special/send1")
    public String specialSend1(){
        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag0", "I'm from tag0");  // tag0 will not be consumer-selected
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag0");
        // consumer: ------- MessageExtConsumer received message, msgId: FE80000000000000AEDE48FFFE001122000018B4AAC246BDAF5E0000, body:I'm from tag0
        return "success";
    }

    //special send : 带tag发送
    @RequestMapping("/special/send2")
    public String specialSend2(){
        rocketMQTemplate.convertAndSend(msgExtTopic + ":tag1", "I'm from tag1");
        System.out.printf("syncSend topic %s tag %s %n", msgExtTopic, "tag1");
        // 由于MessageExtConsumer 监听了tag0||tag1 所以都能收到，如果只监听一个，只能收到其中一个的消息
        // consumer: ------- MessageExtConsumer received message, msgId: FE80000000000000AEDE48FFFE001122000018B4AAC246BEF8440005, body:I'm from tag1
        return "success";
    }

    // 发送一个批量消息
    @RequestMapping("/batch/send")
    public String sendBatchMsg(){
        List<Message> msgs = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            msgs.add(MessageBuilder.withPayload("Hello RocketMQ Batch Msg#" + i).
                    setHeader(RocketMQHeaders.KEYS, "KEY_" + i).build());
        }
        SendResult sr = rocketMQTemplate.syncSend(stringTopic, msgs, 60000);
        System.out.printf("--- Batch messages send result :" + sr);
        /**
         * cosumer received:
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#1
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#2
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#0
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#3
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#6
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#4
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#5
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#7
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#8
         * ------- StringConsumerNewNS received: Hello RocketMQ Batch Msg#9
         */
        return sr.toString();
    }

    /**
     * 异步模式，带响应
     * @return
     */
    @RequestMapping("/async/reply1")
    public String asynWithReply(){
        // Send request in async mode and receive a reply of String type.
        rocketMQTemplate.sendAndReceive(stringRequestTopic, "request string", new RocketMQLocalRequestCallback<String>() {
            @Override public void onSuccess(String message) {
                System.out.printf("send %s and receive %s %n", "request string", message);
            }
            @Override public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
        return "success";
    }

    /**
     * 异步模式，带响应
     * @return
     */
    @RequestMapping("/async/reply2")
    public String asynWithReply2(){
        // Send request in sync mode with timeout parameter and receive a reply of byte[] type.
        byte[] replyBytes = rocketMQTemplate.sendAndReceive(bytesRequestTopic, MessageBuilder.withPayload("request byte[]").build(), byte[].class, 30000);
        System.out.printf("send %s and receive %s %n", "request byte[]", new String(replyBytes));

        return "success";
    }

    /**************验证rocketmq顺序消费***************/
    @RequestMapping("/send/ordered")
    public String sendOrderedMsg(){
        /**
         * hashKey: 为了保证报到同一个队列中，将消息发送到orderTopic主题上
         */
        rocketMQTemplate.syncSendOrderly("orderTopic","no1","order");
        rocketMQTemplate.syncSendOrderly("orderTopic","no2","order");
        rocketMQTemplate.syncSendOrderly("orderTopic","no3","order");
        rocketMQTemplate.syncSendOrderly("orderTopic","no4","order");
        return "success";
    }

    /**************验证rocketmq延迟队列消费***************/
    @RequestMapping("/send/delay")
    public String sendDelayMsg(){
        /**
         * 发送延时消息：
         * 参数1： topic
         * 参数2： Message类型消息
         * 参数3： 超时时间
         * 参数4： 延迟级别： messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
         */
        // 4----30s
        rocketMQTemplate.syncSend("delayTopic",MessageBuilder.withPayload("hello").build(),100,1);
        System.out.println(LocalDateTime.now());
        return "success";
    }
}
