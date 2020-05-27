package com.lsqingfeng.action.es.enums;

import lombok.Getter;

/**
 * es 类型参看
 * https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html
 */
@Getter
public enum FieldType {
    /**
     * text
     */
    TEXT("text"),

    KEYWORD("keyword"),

    INTEGER("integer"),

    DOUBLE("double"),

    DATE("date"),

    /**
     * 单条数据
     */
    OBJECT("object"),

    /**
     * 嵌套数组
     */
    NESTED("nested"),


    ;


    FieldType(String type){
        this.type = type;
    }

    private String type;


}
