package com.lsqingfeng.action.job.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lsqingfeng.action.job.pojo.XxlJobInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: XxlJobUtil
 * @description: xxl-job工具类封装，用于使用rest方式调用xxl-job完成任务的创建修改删除调度
 * @author: sh.Liu
 * @date: 2020-08-27 10:32
 */
@Slf4j
@Component
public class XxlJobUtil {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    private RestTemplate restTemplate = new RestTemplate();

    private static final String ADD_URL = "/jobinfo/addJob";
    private static final String UPDATE_URL = "/jobinfo/updateJob";
    private static final String REMOVE_URL = "/jobinfo/removeJob";
    private static final String PAUSE_URL = "/jobinfo/pauseJob";
    private static final String START_URL = "/jobinfo/startJob";
    private static final String ADD_START_URL = "/jobinfo/addAndStart";
    private static final String GET_GROUP_ID = "/jobgroup/getGroupId";


    public String add(XxlJobInfo jobInfo){
        // 查询对应groupId:
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        String json2 = JSON.toJSONString(jobInfo);
        return doPost(adminAddresses + ADD_URL, json2);
    }

    public String update(int id, String cron){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        param.put("jobCron", cron);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + UPDATE_URL, json);
    }

    public String remove(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + REMOVE_URL, json);
    }

    public String pause(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + PAUSE_URL, json);
    }

    public String start(int id){
        Map<String,Object> param = new HashMap<>();
        param.put("id", id);
        String json = JSON.toJSONString(param);
        return doPost(adminAddresses + START_URL, json);
    }

    public String addAndStart(XxlJobInfo jobInfo){
        Map<String,Object> param = new HashMap<>();
        param.put("appname", appname);
        String json = JSON.toJSONString(param);
        String result = doPost(adminAddresses + GET_GROUP_ID, json);

        JSONObject jsonObject = JSON.parseObject(result);
        String groupId = jsonObject.getString("content");
        jobInfo.setJobGroup(Integer.parseInt(groupId));
        String json2 = JSON.toJSONString(jobInfo);

        return doPost(adminAddresses + ADD_START_URL, json2);
    }

    public String doPost(String url, String json){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json ,headers);
        log.info(entity.toString());
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, entity, String.class);
        return stringResponseEntity.getBody();
    }

}
