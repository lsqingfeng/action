package com.lsqingfeng.action.es.pojo;


import com.lsqingfeng.action.es.annotation.Field;
import com.lsqingfeng.action.es.enums.AnalyzerType;
import com.lsqingfeng.action.es.enums.FieldType;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: EsVisitor
 * @description: 事件回访
 * @author: sh.Liu
 * @date: 2020-07-22 13:29
 */
@Data
public class EsVisitor implements Serializable {
    /**
     * 自增主键
     */
    @Field(type = FieldType.INTEGER)
    private Integer visitorId;

    /**
     * 事件标识
     */
    @Field(type = FieldType.INTEGER)
    private Integer eventId;

    /**
     * 评价人名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String evaluaterName;

    /**
     * 评价人联系方式
     */
    @Field(type = FieldType.KEYWORD)
    private String phone;

    /**
     * 发起人标识
     */
    @Field(type = FieldType.KEYWORD)
    private Integer userId;

    /**
     * 发起人姓名
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String userName;

    /**
     * 发起人部门
     */
    @Field(type = FieldType.INTEGER)
    private Integer unitId;

    /**
     * 发起人部门名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String unitName;

    /**
     * 发起人角色
     */
    @Field(type = FieldType.INTEGER)
    private Integer roleId;

    /**
     * 发起人角色名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String roleName;

    /**
     * 评价等级
     */
    @Field(type = FieldType.INTEGER)
    private Integer evaluateId;

    /**
     * 评价结果显示
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String resultLabel;

    /**
     * 评价内容
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String gridName;

    /**
     * 回访时间
     */
    @Field(type = FieldType.DATE)
    private String visitorTime;

    /**
     * 回访状态： 1-正常回访  2-无法回访
     */
    @Field(type = FieldType.INTEGER)
    private Integer visitorStateId;
}
