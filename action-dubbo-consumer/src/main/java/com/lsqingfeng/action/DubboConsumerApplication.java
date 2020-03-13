package com.lsqingfeng.action;


import com.lsqingfeng.action.dubbo.api.DubboApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
class DubboConsumerApplication {

    @Reference(version = "1.0.0", url="dubbo://127.0.0.1:12345")
    private DubboApi dubboApi;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            log.info(dubboApi.test("mercyblitz"));
        };
    }
}
