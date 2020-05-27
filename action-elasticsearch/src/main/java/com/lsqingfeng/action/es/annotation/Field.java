package com.lsqingfeng.action.es.annotation;

import com.lsqingfeng.action.es.enums.AnalyzerType;
import com.lsqingfeng.action.es.enums.FieldType;

import java.lang.annotation.*;

/**
 * 作用在字段上，用于定义类型，映射关系
 * @author ls
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field  {

    FieldType type() default FieldType.TEXT;

    /**
     * 指定分词器
     * @return
     */
    AnalyzerType analyzer() default AnalyzerType.STANDARD;



}
