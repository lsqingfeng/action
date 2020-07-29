package com.lsqingfeng.action.es.pojo;


import com.lsqingfeng.action.es.annotation.Field;
import com.lsqingfeng.action.es.enums.AnalyzerType;
import com.lsqingfeng.action.es.enums.FieldType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @className: EsAct
 * @description: es act表
 * @author: sh.Liu
 * @date: 2020-07-22 13:18
 */
@Data
public class EsAct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(type = FieldType.INTEGER)
    private Integer actId;

    /**
     * 任务标识
     */
    @Field(type = FieldType.KEYWORD)
    private String taskId;

    /**
     * 流程定义标识
     */
    @Field(type = FieldType.KEYWORD)
    private String procDefId;

    /**
     * 流程实例标识
     */
    @Field(type = FieldType.KEYWORD)
    private String procInstId;

    /**
     * 子流程实例标识
     */
    @Field(type = FieldType.KEYWORD)
    private String subInstId;

    /**
     * 节点定义标识
     */
    @Field(type = FieldType.KEYWORD)
    private String nodeId;

    /**
     * 节点定义名称
     */
    @Field(type = FieldType.KEYWORD)
    private String nodeName;

    /**
     * 业务主键标识
     */
    @Field(type = FieldType.KEYWORD)
    private String bizId;

    /**
     * 参与者标识
     */
    @Field(type = FieldType.KEYWORD)
    private String partId;
    /**
     * 参与者姓名
     */
    @Field(type = FieldType.TEXT)
    private String partName;
    /**
     * 部门id
     */
    @Field(type = FieldType.KEYWORD)
    private String unitId;
    /**
     * 部门名称
     */
    @Field(type = FieldType.TEXT)
    private String unitName;

    /**
     * 角色标识
     */
    @Field(type = FieldType.KEYWORD)
    private String roleId;

    /**
     * 角色名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String roleName;

    /**
     * 上一活动标识
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String preActId;

    /**
     * 上一活动参与者标识
     */
    private String prePartId;

    /**
     * 上一活动参与者名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String prePartName;

    /**
     * 上一活动定义标识
     */
    private String preNodeId;

    /**
     * 上一活动定义名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String preNodeName;

    /**
     * 上一活动意见
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String preOpinion;

    /**
     * 上一活动操作项名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String preActionName;

    /**
     * 上一活动操作项显示名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String preActionLabel;

    /**
     * 创建时间
     */
    @Field(type = FieldType.DATE)
    private String createTime;

    /**
     * 截止时间
     */
    @Field(type = FieldType.DATE)
    private String deadLine;

    /**
     * 警告时间
     */
    @Field(type = FieldType.DATE)
    private String warningTime;

    /**
     * 结束时间
     */
    @Field(type = FieldType.DATE)
    private String endTime;

    /**
     * 活动红绿灯
     */
    @Field(type = FieldType.INTEGER)
    private Integer actTimeStateId;

    /**
     * 活动红绿灯--冗余
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actTimeStateName;

    /**
     * 活动时限
     */
    @Field(type = FieldType.DOUBLE)
    private BigDecimal timeLimit;

    /**
     * 计时单位
     */
    @Field(type = FieldType.INTEGER)
    private Integer timeUnit;

    /**
     * 活动时限分钟
     */
    @Field(type = FieldType.INTEGER)
    private Integer timeLimitM;

    /**
     * 已用时
     */
    @Field(type = FieldType.INTEGER)
    private Integer actUsedTime;

    /**
     * 剩余时
     */
    @Field(type = FieldType.INTEGER)
    private Integer actRemainTime;

    /**
     * 活动时限信息
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actLimitInfo;

    /**
     * 活动已用时间字符串
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actUsedTimeChar;

    /**
     * 活动剩余时间字符串
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actRemainTimeChar;

    /**
     * 累计计时标志
     */
    @Field(type = FieldType.INTEGER)
    private Integer timeAddUpFlag;

    /**
     * 暂停前节点用时
     */
    @Field(type = FieldType.INTEGER)
    private Integer actUsedTimeBeforeStop;

    /**
     * 恢复计时时间
     */
    @Field(type = FieldType.DATE)
    private String actRestart;

    /**
     * 已读标志
     */
    @Field(type = FieldType.INTEGER)
    private Integer readFlag;

    /**
     * 已读时间
     */
    @Field(type = FieldType.DATE)
    private String readTime;

    /**
     * 批转意见
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String transOpinion;

    /**
     * 操作项名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actionName;

    /**
     * 操作项显示名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actionLabel;

    /**
     * 活动属性id
     */
    @Field(type = FieldType.INTEGER)
    private Integer actPropertyId;

    /**
     * 活动属性名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String actPropertyName;

    /**
     * 抄送参与者
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String ccPart;

    /**
     * 抄送参与者名称
     */
    @Field(type = FieldType.TEXT, analyzer = AnalyzerType.IK_SMART)
    private String ccPartName;

    /**
     * 抄送标志
     */
    @Field(type = FieldType.INTEGER)
    private Integer ccFlag;
}
