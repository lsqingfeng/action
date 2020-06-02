package com.lsqingfeng.action.knowledge.copy;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @className: Person
 * @description:
 * @author: sh.Liu
 * @date: 2020-06-01 13:55
 */
@Data
public class Person {
    private int id;

    private String name;

    private Boolean vipFlag;

    private BigDecimal price;

    private Date createTime;

    private LocalDateTime updateTime;
}
