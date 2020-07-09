package com.lsqingfeng.action.es.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lvhuimin
 * @since 2020-03-09
 */
@Data
@TableName("t_cg_act")
public class CooActDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "act_id", type = IdType.AUTO)
    private Integer actId;

    /**
     * 任务标识
     */
    private String taskId;

    /**
     * 流程定义标识
     */
    private String procDefId;

    /**
     * 流程实例标识
     */
    private String procInstId;

    /**
     * 子流程实例标识
     */
    private String subInstId;

    /**
     * 节点定义标识
     */
    private String nodeId;

    /**
     * 节点定义名称
     */
    private String nodeName;

    /**
     * 业务主键标识
     */
    private String bizId;

    /**
     * 参与者标识
     */
    private String partId;

    @TableField(value = "part_name", updateStrategy = FieldStrategy.IGNORED)
    private String partName;

    @TableField(value = "unit_id", updateStrategy = FieldStrategy.IGNORED)
    private String unitId;

    @TableField(value = "unit_name", updateStrategy = FieldStrategy.IGNORED)
    private String unitName;

    /**
     * 角色标识
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 上一活动标识
     */
    private String preActId;

    /**
     * 上一活动参与者标识
     */
    private String prePartId;

    /**
     * 上一活动参与者名称
     */
    private String prePartName;

    /**
     * 上一活动定义标识
     */
    private String preNodeId;

    /**
     * 上一活动定义名称
     */
    private String preNodeName;

    /**
     * 上一活动意见
     */
    private String preOpinion;

    /**
     * 上一活动操作项名称
     */
    private String preActionName;

    /**
     * 上一活动操作项显示名称
     */
    private String preActionLabel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 截止时间
     */
    private Date deadLine;

    /**
     * 警告时间
     */
    private Date warningTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 活动红绿灯
     */
    private Integer actTimeStateId;

    /**
     * 活动时限
     */
    private BigDecimal timeLimit;

    /**
     * 计时单位
     */
    private Integer timeUnit;

    /**
     * 活动时限分钟
     */
    private Integer timeLimitM;

    /**
     * 已用时
     */
    private Integer actUsedTime;

    /**
     * 剩余时
     */
    private Integer actRemainTime;

    /**
     * 活动时限信息
     */
    private String actLimitInfo;

    /**
     * 活动已用时间字符串
     */
    private String actUsedTimeChar;

    /**
     * 活动剩余时间字符串
     */
    private String actRemainTimeChar;

    /**
     * 累计计时标志
     */
    private Integer timeAddUpFlag;

    /**
     * 暂停前节点用时
     */
    private Integer actUsedTimeBeforeStop;

    /**
     * 恢复计时时间
     */
    private Date actRestart;

    /**
     * 已读标志
     */
    private Integer readFlag;

    /**
     * 已读时间
     */
    @TableField(value = "read_time", updateStrategy = FieldStrategy.IGNORED)
    private Date readTime;

    /**
     * 批转意见
     */
    private String transOpinion;

    /**
     * 操作项名称
     */
    private String actionName;

    /**
     * 操作项显示名称
     */
    private String actionLabel;

    /**
     * 活动属性id
     */
    private Integer actPropertyId;

    /**
     * 活动属性名称
     */
    private String actPropertyName;

    /**
     * 抄送参与者
     */
    private String ccPart;

    /**
     * 抄送参与者名称
     */
    private String ccPartName;

    /**
     * 抄送标志
     */
    private Integer ccFlag;


}
