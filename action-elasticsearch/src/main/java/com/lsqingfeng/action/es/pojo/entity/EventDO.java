package com.lsqingfeng.action.es.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@TableName("t_cg_event")
public class EventDO implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "event_id", type = IdType.AUTO)
    private Integer eventId;

    /**
     * 唯一标识码
     */
    private String uniqueCode;

    /**
     * 任务号
     */
    private String eventCode;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer eventSrcId;

    /**
     * 事件来源名称
     */
    private String eventSrcName;

    /**
     * 来源分组
     */
    private String srcGroupCode;

    @NotBlank(message = "事件大类编码不能为空")
    private String eventTypeCode;

    /**
     * 事件大类名称
     */
    private String eventTypeName;

    @NotBlank(message = "事件小类编码不能为空")
    private String eventSubtypeCode;

    /**
     * 事件小类名称
     */
    private String eventSubtypeName;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @NotNull(message = "重要程度标识不能为空")
    private Integer eventGradeId;

    /**
     * 重要程度名称
     */
    private String eventGradeName;

    /**
     *紧急程度标识
     */
    private Integer eventUrgencyId;

    /**
     *紧急程度名称
     */
    private String eventUrgencyName;

    /**
     *事件级别标识
     */
    private Integer eventLevelId;

    /**
     *事件级别名称
     */
    private String eventLevelName;

    /**
     *事件升级标志
     */
    private Integer eventUpgradeFlag;

    /**
     *处置级别标识
     */
    private Integer dealLevelId;

    /**
     *处置级别标识
     */
    private String dealLevelName;

    /**
     *公众上报人名称
     */
    private String publicReporterName;

    /**
     *公众上报人身份证号
     */
    private String publicReporterIdcard;

    /**
     *公众上报人联系方式
     */
    private String publicReporterTel;

    @NotBlank(message = "事件描述不能为空")
    private String eventDesc;

    @NotBlank(message = "地址描述不能为空")
    private String address;

    @NotBlank(message = "地区名称不能为空")
    private String areaRegionName;

    @NotBlank(message = "地区编码不能为空")
    private String areaRegionCode;

    /**
     * 社区名称
     */
//    @NotBlank(message = "社区名称不能为空")
    private String commRegionName;

    /**
     * 区编码
     */
//    @NotBlank(message = "社区编码不能为空")
    private String commRegionCode;

    /**
     * 街道名称
     */
//    @NotBlank(message = "街道名称不能为空")
    private String streetRegionName;

    /**
     * 街道编码
     */
//    @NotBlank(message = "街道编码不能为空")
    private String streetRegionCode;

    /**
     * 社区名称
     */
    private String regionName;

    /**
     * 社区编码
     */
    private String regionCode;

    /**
     * 经度
     */
    private BigDecimal coordX;

    /**
     * 纬度
     */
    private BigDecimal coordY;

    /**
     *坐标系
     */
    private String mapcoordinate;

    /**
     *网格员标识
     */
    private Integer griderId;

    /**
     *网格员标识
     */
    private String griderName;

    /**
     *网格员电话
     */
    private String griderPhone;

    /**
     *核实状态标识
     */
    private Integer verifyStateId;

    /**
     *核查状态标识
     */
    private Integer checkStateId;

    /**
     *事件建立时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     *流程结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    /**
     *延期天数
     */
    private Float postponedDays;

    /**
     *延期标志
     */
    private Integer postponedFlag;

    /**
     *受理时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date acceptTime;

    /**
     *立案时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date establishTime;

    /**
     *调度时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dispatchTime;

    /**
     *流程开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date procStartTime;

    /**
     *流程结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date procEndTime;

    /**
     *流程截止时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date procDeadLine;

    /**
     *流程警告时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date procWarningTime;

    /**
     *处置开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date funcBeginTime;

    /**
     *处置完成时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date funcFinishTime;

    /**
     *自处置标识
     */
    private Integer gridDealFlag;

    /**
     *跨网格标志
     */
    private Integer overGridFlag;

    /**
     *是否督办
     */
    private Integer pressFlag;

    /**
     *是否催办
     */
    private Integer hurryFlag;

    /**
     *超期标志
     */
    private Integer overtimeFlag;

    /**
     *活动属性
     */
    private Integer actPropertyId;

    /**
     *活动属性名称
     */
    private String actPropertyName;

    /**
     *流程实例标识
     */
    private String procInstId;

    /**
     *流程定义标识
     */
    private String procDefId;

    /**
     *事件状态
     */
    private Integer eventStateId;

    /**
     * 上一操作项
     */
    private String preActionName;

    /**
     * 登记人Id
     */
    private Integer registerId;

    /**
     * 登记人姓名
     */
    private String registerName;

    /**
     * 回访标识：
     */
    private Integer visitorStateId;

    /**
     * 删除标识
     */
    private Integer deleteFlag;

    /**
     * 删除用户标识
     */
    private Integer deleteUserId;

    /**
     * 删除时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date deleteTime;

    /**
     * actionName
     */
    @TableField(exist = false)
    private String actionName;


    /**
     * actionLable
     */
    @TableField(exist = false)
    private String actionLabel;

    /**
     * 是否下发督查
     * 0：否，1：是
     */
    private Integer overseerFlag;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


    @Override
    public String toString() {
        return "EventDO{" +
                "eventId=" + eventId +
                ", uniqueCode=" + uniqueCode +
                ", eventCode=" + eventCode +
                ", eventSrcId=" + eventSrcId +
                ", eventSrcName=" + eventSrcName +
                ", eventTypeCode=" + eventTypeCode +
                ", eventTypeName=" + eventTypeName +
                ", eventSubtypeCode=" + eventSubtypeCode +
                ", eventSubtypeName=" + eventSubtypeName +
                ", eventGradeId=" + eventGradeId +
                ", eventGradeName=" + eventGradeName +
                ", eventUrgencyId=" + eventUrgencyId +
                ", eventUrgencyName=" + eventUrgencyName +
                ", eventLevelId=" + eventLevelId +
                ", eventLevelName=" + eventLevelName +
                ", eventUpgradeFlag=" + eventUpgradeFlag +
                ", dealLevelId=" + dealLevelId +
                ", dealLevelName=" + dealLevelName +
                ", publicReporterName=" + publicReporterName +
                ", publicReporterIdcard=" + publicReporterIdcard +
                ", publicReporterTel=" + publicReporterTel +
                ", eventDesc=" + eventDesc +
                ", address=" + address +
                ", areaRegionName=" + areaRegionName +
                ", areaRegionCode=" + areaRegionCode +
                ", commRegionName=" + commRegionName +
                ", commRegionCode=" + commRegionCode +
                ", streetRegionName=" + streetRegionName +
                ", streetRegionCode=" + streetRegionCode +
                ", regionName=" + regionName +
                ", regionCode=" + regionCode +
                ", mapcoordinate=" + mapcoordinate +
                ", griderId=" + griderId +
                ", griderName=" + griderName +
                ", griderPhone=" + griderPhone +
                ", verifyStateId=" + verifyStateId +
                ", checkStateId=" + checkStateId +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", postponedDays=" + postponedDays +
                ", postponedFlag=" + postponedFlag +
                ", acceptTime=" + acceptTime +
                ", establishTime=" + establishTime +
                ", dispatchTime=" + dispatchTime +
                ", procStartTime=" + procStartTime +
                ", procEndTime=" + procEndTime +
                ", procDeadLine=" + procDeadLine +
                ", procWarningTime=" + procWarningTime +
                ", funcBeginTime=" + funcBeginTime +
                ", funcFinishTime=" + funcFinishTime +
                ", gridDealFlag=" + gridDealFlag +
                ", overGridFlag=" + overGridFlag +
                ", pressFlag=" + pressFlag +
                ", hurryFlag=" + hurryFlag +
                ", overtimeFlag=" + overtimeFlag +
                ", actPropertyId=" + actPropertyId +
                ", actPropertyName=" + actPropertyName +
                ", procInstId=" + procInstId +
                ", procDefId=" + procDefId +
                ", eventStateId=" + eventStateId +
                ", preActionName=" + preActionName +
                ", registerId=" + registerId +
                ", registerName=" + registerName +
                "}";
    }


}

