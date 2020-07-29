package com.lsqingfeng.action.es.pojo.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 综合查询对应javabean
 *
 * @author wanggang
 * @date 2020/1/3
 */
@Data
//@ColumnWidth(30)
public class ComprehensiveQueryEventVO implements Serializable {

//    @ExcelProperty("序号")
    private Integer orderNum;

    /**
     * 任务号
     */
//    @ExcelProperty("事件编号")
    private String eventCode;

    /**
     * 事件大类名称
     */
//    @ExcelProperty("类型")
    private String eventTypeName;

    /**
     * 事件大类编码
     */
//    @ExcelProperty("事件大类编码")
    private String eventTypeCode;

    /**
     * 事件小类编码
     */
//    @ExcelProperty("事件小类编码")
    private String eventSubtypeCode;

    /**
     * 事件小类名称
     */
//    @ExcelProperty("事件小类名称")
    private String eventSubtypeName;

    /**
     * 地区名称
     */
//    @ExcelProperty("地区名称")
    private String areaRegionName;

    /**
     * 地区编码
     */
    private String areaRegionCode;

    /**
     * 社区名称
     */
//    @ExcelProperty("社区名称")
    private String commRegionName;

    /**
     * 社区编码
     */
    private String commRegionCode;

    /**
     * 街道名称
     */
//    @ExcelProperty("街道名称")
    private String streetRegionName;

    /**
     * 街道编码
     */
    private String streetRegionCode;

    /**
     * 社区名称
     */
//    @ExcelProperty("区域")
    private String regionName;

    /**
     * 社区编码
     */
    private String regionCode;

    /**
     * 地址描述
     */
//    @ExcelProperty("地址")
    private String address;

    /**
     * 重要程度标识
     */
//    @ExcelProperty("等级标识")
    private Integer eventGradeId;

    /**
     * 重要程度名称
     */
//    @ExcelProperty("等级")
    private String eventGradeName;

    /**
     * 活动状态标识
     */
    private Integer actTimeStateId;

    /**
     * 活动状态名称
     */
//    @ExcelProperty("计时状态")
    private String actTimeStateName;

    /**
     * 是否督办
     */
//    @ExcelProperty("是否督办标志位")
    private Integer pressFlag;

//    @ExcelProperty("督办")
    private String pressFlagName;

    /**
     * 是否督查
     */
//    @ExcelProperty("是否督查标志位")
    private Integer overseerFlag;

//    @ExcelProperty("督查")
    private String overseerFlagName;

    /**
     * 流程开始时间
     */
//    @ExcelProperty("上报时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String procStartTime;

    /**
     * 流程截止时间
     */
//    @ExcelProperty("截止时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String procDeadLine;

    /**
     * 事件描述
     */
//    @ExcelProperty("事件描述")
    private String eventDesc;

    /**
     * 事件状态
     */
//    @ExcelProperty("事件状态Id")
    private Integer eventStateId;

//    @ExcelProperty("事件状态")
    private String eventStateName;

    /**
     * 事件来源标识
     */
//    @ExcelProperty("事件来源标识")
    private Integer eventSrcId;

    /**
     * 事件来源名称
     */
//    @ExcelProperty("事件来源")
    private String eventSrcName;

    /**
     * 流程结束时间
     */
//    @ExcelProperty("办结时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String procEndTime;

    /**
     * 超时时长
     */
//    @ExcelProperty("超时时长")
    private String timeoutDuration;

    /**
     * 回访时间
     */
//    @ExcelProperty("回访时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String visitorTime;

    /**
     * 满意度
     */
//    @ExcelProperty("满意度")
    private String resultLabel;

    /**
     * 上报开始时间
     */
//    @ExcelProperty("上报开始时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String reportStartTime;

    /**
     * 上报结束时间
     */
//    @ExcelProperty("上报结束时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String reportEndTime;

    /**
     * 截止开始时间
     */
//    @ExcelProperty("截止开始时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String deadLineStartTime;

    /**
     * 截止结束时间
     */
//    @ExcelProperty("截止结束时间")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String deadLineEndTime;

    /**
     * 上报人电话
     */
//    @ExcelProperty("上报人")
    private String publicReporterName;

//    @ExcelProperty("联系电话")
    private String phone;

    /**
     * 超时状态（流程红绿灯）
     */
    private Integer procTimeStateId;

    /**
     * 超时状态对应名称
     */
    private String procTimeStateName;

    /**
     * 导出字段List
     */
    private List<String> exportColumnList;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 活动截止时间
     */
    private String deadLine;

    private Integer pageNum;

    private Integer pageSize;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方式
     */
    private String orderSort;

    /**
     * 事件Id
     */
    private Integer eventId;


}

