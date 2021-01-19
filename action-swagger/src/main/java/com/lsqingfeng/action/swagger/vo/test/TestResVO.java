package com.lsqingfeng.action.swagger.vo.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className: TestResVO
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:20
 */
@Data
@ApiModel("Test返回结果")
public class TestResVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("啥啥")
    private String what;

}
