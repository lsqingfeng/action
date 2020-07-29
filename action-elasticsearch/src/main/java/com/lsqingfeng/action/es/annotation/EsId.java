package com.lsqingfeng.action.es.annotation;

import java.lang.annotation.*;

/**
 * 用于标识使用 该字段作为ES数据中的id
 * @author sh.Liu
 * @create: 2020-07-22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface EsId {
}
