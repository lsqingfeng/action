package com.lsqingfeng.action.es.controller;

import com.lsqingfeng.action.es.pojo.Event;
import com.lsqingfeng.action.es.util.EsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: EventController
 * @description: 测试事件通过elaticSearch 查询
 * @author: sh.Liu
 * @create: 2020-05-27 09:59
 */
@RestController
@RequestMapping("/event")
public class EventController {

    public static final String indexName = "event";

    @Autowired
    private EsUtil esUtil;

    @RequestMapping("create")
    public boolean createIndex() throws Exception {
        boolean re = esUtil.isIndexExists(indexName);
        if(re){
            esUtil.delIndex(indexName);
        }

        boolean re1 = esUtil.createIndex(Event.class);
        return re1;
    }

    /**
     * 批量插入文档
     * @return
     */
    @RequestMapping("saveBatch")
    public boolean saveBatch(){

        return true;
    }






}
