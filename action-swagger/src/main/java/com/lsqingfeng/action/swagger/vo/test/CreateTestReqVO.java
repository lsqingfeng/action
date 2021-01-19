package com.lsqingfeng.action.swagger.vo.test;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className: CreateTestReqVO
 * @description:
 * @author: sh.Liu
 * @date: 2021-01-19 16:13
 */
@Data
@ApiModel("创建Test请求参数")
public class CreateTestReqVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

}
