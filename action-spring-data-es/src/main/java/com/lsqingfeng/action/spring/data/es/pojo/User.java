package com.lsqingfeng.action.spring.data.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @className: Person
 * @description:
 * @author: sh.Liu
 * @date: 2020-07-09 15:29
 */
@Data
@Document(indexName = "user")
public class User implements Serializable {

    @Id
    @Field(type= FieldType.Long, store = true,analyzer = "ik_max_word")
    private Long id;

    @Field(type= FieldType.Text, store = true,analyzer = "ik_max_word")
    private String userName;

    @Field(type= FieldType.Text, store = true,analyzer = "ik_max_word")
    private String nickName;

    @Field(type= FieldType.Text, store = true,analyzer = "ik_max_word")
    private Integer age;

    @Field(type= FieldType.Text, store = true,analyzer = "ik_max_word")
    private String gender;

    @Field(type= FieldType.Text, store = true,analyzer = "ik_max_word")
    private String hobby;




}
