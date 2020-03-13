package com.lsqingfeng.action.dubbo.provider;

import com.lsqingfeng.action.dubbo.api.DubboApi;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * provider: 服务的提供者，也是接口的实现者，
 * 使用@Service 注解暴露接口，要注意这个注解是dubbo的，不是spring的
 */
@Service(version = "1.0.0")
public class DubboApiImpl implements DubboApi {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String test(String name) {
        System.out.println("大家好我是dobbo的实现者，如果看到这句话，证明你已经使用dubbo技术调用到了我");
        return String.format("[%s] : Hello, %s", serviceName, name);
    }
}
