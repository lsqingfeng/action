package com.lsqingfeng.action.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理系统用户表
 * </p>
 *
 * @author lsqingfeng
 * @since 2020-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("man_user")
public class ManUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户账号
     */
    private String passport;

    /**
     * 用户密码哈希值
     */
    private String passwordHash;

    /**
     * 密码加密的盐值
     */
    private String passwordSalt;

    /**
     * 状态 10启用 20禁用
     */
    private String state;

    /**
     * 用户邮件
     */
    private String email;

    /**
     * 用户电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 登陆时间，首次添加用户后为null，首次登录后存值
     */
    private LocalDateTime loginTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
