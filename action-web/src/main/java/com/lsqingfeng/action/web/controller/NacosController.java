package com.lsqingfeng.action.web.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * nacos: 演示
 * nacos 配置页面地址：http://192.168.1.11:8848/nacos/#/listeningToQuery?serverId=center&group=&dataId=&namespace=&appName=
 */
@RestController
@RequestMapping("/nacos")
public class NacosController {

    @NacosInjected
    private ConfigService configService;

    @NacosValue(value = "${test:0}", autoRefreshed = true)
    private int test;

    /**
     * http://localhost:9999/nacos/get
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public int get() {
        return test;
    }

    /**
     * http://127.0.0.1:9999/nacos/publish?dataId=action_config&content=test=900
     * @param dataId
     * @param group
     * @param content
     * @return
     * @throws NacosException
     */
    @RequestMapping(value="/publish" ,method = RequestMethod.GET)
    public ResponseEntity<String> publish(@RequestParam String dataId,
                                          @RequestParam(defaultValue = "DEFAULT_GROUP") String group,
                                          @RequestParam String content) throws NacosException {
        boolean result = configService.publishConfig(dataId, group, content);
        if (result) {
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
