/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : action

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 13/03/2020 09:05:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for man_map_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `man_map_role_menu`;
CREATE TABLE `man_map_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单主键',
  `read_permission` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '读权限',
  `write_permission` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '写权限',
  `detail_permission` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '查看详情权限',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_role_id_menu_id` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='管理系统关系映射表(角色-菜单)';

-- ----------------------------
-- Table structure for man_map_user_role
-- ----------------------------
DROP TABLE IF EXISTS `man_map_user_role`;
CREATE TABLE `man_map_user_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色主键',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_user_id_role_id` (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='管理系统关系映射表(用户-角色)';

-- ----------------------------
-- Table structure for man_menu
-- ----------------------------
DROP TABLE IF EXISTS `man_menu`;
CREATE TABLE `man_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) unsigned NOT NULL COMMENT '父级ID',
  `href` varchar(255) NOT NULL COMMENT '链接地址',
  `component` varchar(255) NOT NULL COMMENT '路由对应本地文件地址',
  `state` varchar(10) NOT NULL DEFAULT '20' COMMENT '状态 10启用 20禁用',
  `sort` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `script_name` varchar(100) DEFAULT NULL COMMENT '脚本名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id_sort` (`parent_id`,`sort`) USING BTREE,
  KEY `idx_sort` (`sort`) USING BTREE,
  KEY `idx_state` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='管理系统菜单表';

-- ----------------------------
-- Table structure for man_role
-- ----------------------------
DROP TABLE IF EXISTS `man_role`;
CREATE TABLE `man_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `uid` varchar(50) NOT NULL COMMENT '角色唯一标示',
  `state` varchar(10) NOT NULL DEFAULT '20' COMMENT '状态 10启用 20禁用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_uid` (`uid`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_state` (`state`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='管理系统角色表';

-- ----------------------------
-- Table structure for man_user
-- ----------------------------
DROP TABLE IF EXISTS `man_user`;
CREATE TABLE `man_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(50) NOT NULL COMMENT '用户姓名',
  `passport` varchar(50) NOT NULL COMMENT '用户账号',
  `password_hash` varchar(255) NOT NULL COMMENT '用户密码哈希值',
  `password_salt` varchar(255) NOT NULL COMMENT '密码加密的盐值',
  `state` varchar(10) NOT NULL DEFAULT '20' COMMENT '状态 10启用 20禁用',
  `email` varchar(50) DEFAULT NULL COMMENT '用户邮件',
  `phone` varchar(50) DEFAULT NULL COMMENT '用户电话',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `login_time` datetime DEFAULT NULL COMMENT '登陆时间，首次添加用户后为null，首次登录后存值',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_passport` (`passport`) USING BTREE,
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_email` (`email`) USING BTREE,
  KEY `idx_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='管理系统用户表';

-- ----------------------------
-- Records of man_user
-- ----------------------------
BEGIN;
INSERT INTO `man_user` VALUES (1, 'zhangsan', '123456', '123456', 'abcd', '20', NULL, '18888888888', NULL, NULL, NULL, 'system', '2020-03-10 12:21:58', NULL, '2020-03-10 12:21:58');
INSERT INTO `man_user` VALUES (19, 'zhangsan1', '1234561', '1234561', 'abcd1', '20', NULL, '18888888881', NULL, NULL, NULL, 'system1', '2020-03-10 12:38:25', NULL, '2020-03-10 12:38:25');
INSERT INTO `man_user` VALUES (20, 'zhangsan2', '1234562', '1234562', 'abcd2', '20', NULL, '18888888882', NULL, NULL, NULL, 'system2', '2020-03-10 12:38:30', NULL, '2020-03-10 12:38:30');
INSERT INTO `man_user` VALUES (21, 'zhangsan3', '1234563', '1234563', 'abcd3', '20', NULL, '18888888883', NULL, NULL, NULL, 'system3', '2020-03-10 12:38:35', NULL, '2020-03-10 12:38:35');
INSERT INTO `man_user` VALUES (22, 'zhangsan4', '1234564', '1234564', 'abcd4', '20', NULL, '18888888884', NULL, NULL, NULL, 'system4', '2020-03-10 12:38:40', NULL, '2020-03-10 12:38:40');
INSERT INTO `man_user` VALUES (23, 'zhangsan5', '1234565', '1234565', 'abcd5', '20', NULL, '18888888885', NULL, NULL, NULL, 'system5', '2020-03-10 12:38:45', NULL, '2020-03-10 12:38:45');
INSERT INTO `man_user` VALUES (24, 'zhangsan6', '1234566', '1234566', 'abcd6', '20', NULL, '18888888886', NULL, NULL, NULL, 'system6', '2020-03-10 12:38:50', NULL, '2020-03-10 12:38:50');
INSERT INTO `man_user` VALUES (25, 'zhangsan7', '1234567', '1234567', 'abcd7', '20', NULL, '18888888887', NULL, NULL, NULL, 'system7', '2020-03-10 12:38:55', NULL, '2020-03-10 12:38:55');
INSERT INTO `man_user` VALUES (26, 'zhangsan8', '1234568', '1234568', 'abcd8', '20', NULL, '18888888888', NULL, NULL, NULL, 'system8', '2020-03-10 12:39:00', NULL, '2020-03-10 12:39:00');
COMMIT;

-- ----------------------------
-- Table structure for quartz_info
-- ----------------------------
DROP TABLE IF EXISTS `quartz_info`;
CREATE TABLE `quartz_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(50) NOT NULL COMMENT '定时任务名称',
  `code` varchar(50) NOT NULL COMMENT '定时任务code标识',
  `cycle` varchar(50) NOT NULL COMMENT '定时任务执行周期',
  `succeed` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '成功执行次数',
  `fail` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '失败执行次数',
  `state` varchar(10) NOT NULL DEFAULT '20' COMMENT '状态 10启用 20禁用',
  `create_user` varchar(50) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统定时任务信息表';

-- ----------------------------
-- Records of quartz_info
-- ----------------------------
BEGIN;
INSERT INTO `quartz_info` VALUES (1, '测试任务', 'myTestTask', '0/5 * * * * ?', 0, 0, '20', '123', '2020-03-10 15:12:59', '123', '2020-03-12 11:05:21');
INSERT INTO `quartz_info` VALUES (3, '测试任务2', 'myTestTask2', '0/2 * * * * ?', 0, 0, '20', 'system', '2020-03-10 18:38:52', '123', '2020-03-12 11:05:25');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
