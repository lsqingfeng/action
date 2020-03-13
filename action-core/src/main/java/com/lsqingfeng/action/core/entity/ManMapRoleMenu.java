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
 * 管理系统关系映射表(角色-菜单)
 * </p>
 *
 * @author lsqingfeng
 * @since 2020-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("man_map_role_menu")
public class ManMapRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色主键
     */
    private Long roleId;

    /**
     * 菜单主键
     */
    private Long menuId;

    /**
     * 读权限
     */
    private Integer readPermission;

    /**
     * 写权限
     */
    private Integer writePermission;

    /**
     * 查看详情权限
     */
    private Integer detailPermission;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
