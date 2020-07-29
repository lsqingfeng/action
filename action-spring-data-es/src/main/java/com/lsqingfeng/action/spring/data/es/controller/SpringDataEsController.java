package com.lsqingfeng.action.spring.data.es.controller;

import com.alibaba.fastjson.JSON;
import com.lsqingfeng.action.spring.data.es.pojo.User;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: SpringDataEsController
 * @description:
 * @author: sh.Liu
 * @date: 2020-07-09 15:39
 */
@RestController
@RequestMapping("/")
public class SpringDataEsController {

    private ElasticsearchOperations elasticsearchOperations;

    public SpringDataEsController(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }


    @RequestMapping("create")
    public String createIndex() {
        elasticsearchOperations.deleteIndex(User.class);
        elasticsearchOperations.createIndex(User.class);
        return "success";
    }

    @RequestMapping("save")
    public String save() {
        User u = new User();
        u.setId(1L);
        u.setAge(10);
        u.setGender("男");
        u.setHobby("游泳打球看电影");
        u.setNickName("James");
        u.setUserName("Lebron James");

        IndexQuery indexQuery = new IndexQueryBuilder()
        .withId(u.getId().toString())
        .withObject(u)
        .build();
        String documentId = elasticsearchOperations.index(indexQuery);
        return documentId;
    }

    /**
     * 批量保存
     */
    @RequestMapping("batchSave")
    public String batchSave(){
        List<IndexQuery> indexes = new ArrayList<>();
        for (int i=1;i <10; i++) {
            User u = new User();
            u.setId(1L + i);
            u.setAge(10 + i);
            u.setGender("男");
            u.setHobby("游泳打球看电影" + i);
            u.setNickName("James" + i);
            u.setUserName("Lebron James" + i);
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(u.getId().toString())
                    .withObject(u)
                    .build();

            indexes.add(indexQuery);
        }

        elasticsearchOperations.bulkIndex(indexes);
        return "success";
    }

    @RequestMapping("search")
    public String search(@RequestParam String param){

        StringQuery stringQuery = new StringQuery(param);

        List<User> users = elasticsearchOperations.queryForList(stringQuery, User.class);
        return JSON.toJSONString(users);
    }






}
