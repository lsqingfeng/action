package com.lsqingfeng.action.es.controller;

import com.lsqingfeng.action.es.pojo.Person;
import com.lsqingfeng.action.es.util.EsUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @className: TestController
 * @description:
 * @author: sh.Liu
 * @create: 2020-05-22 13:33
 */
@RestController
@RequestMapping("/")
public class TestController {

   @Autowired
   private RestHighLevelClient restHighLevelClient;

   @Autowired
   private EsUtil esUtil;

    @RequestMapping("/createIndex")
   public String createIndex() throws IOException {
       esUtil.createIndex("person");
       return "success";
   }

    @RequestMapping("/delIndex")
    public String delIndex() throws IOException {
        esUtil.delIndex("person");
        return "success";
    }

    @RequestMapping("/add")
    public String add() throws IOException {
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setAge(10+i);
            p.setName("张"+i);
            p.setDesc("我叫" + p.getName() + "我的年龄是" + p.getAge() + "，我是一个好人");
            p.setId(1+i);
            esUtil.add("person", p);
        }
        return "success";
    }

    @RequestMapping("/getById")
    public String getById() throws IOException {
        String s = esUtil.queryById("person", "eqVUSnIBQqpxiwY5KcO6");
        return s;
    }

    @RequestMapping("/search")
    public String search(@RequestParam String word) throws IOException {
        String s = esUtil.search("person", word);
        return s;
    }


    @RequestMapping("/person")
    public String save() {

        Person person = new Person();
        person.setAge(22);
        person.setName("张三");
        person.setDesc("我是一个好人");


        return "success";
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id")  Long id) {
        /*Person person = elasticsearchOperations
                .queryForObject(GetQuery.getById(id.toString()), Person.class);
        return person;*/

        return null;
    }

}
