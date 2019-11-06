
### 管理后台建库

```sql
CREATE DATABASE IF NOT EXISTS blzo_manage DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER blzo@'%' identified BY '123456';
GRANT ALL PRIVILEGES ON blzo_manage.* TO blzo@'%';
```

### 管理后台建表

> 执行后请手动把 admins 表中的超级管理员的id 改为 0 , organizes 表中总组织id 改为0

username: root
password: 1234abc

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员Id',
  `nick_name` varchar(64) NOT NULL DEFAULT '',
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `google_secret` varchar(255) DEFAULT NULL COMMENT 'google验证码',
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:初始状态 1:正常使用 -1:冻结',
  `last_ip` varchar(20) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `layer` varchar(2048) DEFAULT NULL COMMENT '菜单layer',
  `organize_id` int(11) DEFAULT NULL COMMENT '组织id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_KEY` (`username`) USING BTREE,
  KEY `phone_KEY` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admins
-- ----------------------------
BEGIN;
INSERT INTO `admins` VALUES (0, '超级管理员', 'root', '151bddb91e746a4050e550063daff028', '501dd805a0024358ab819b4e312d0fcd', '', '123', NULL, 1, '223.88.57.32', '2019-07-21 22:25:12', '123', '[{\"name\":\"公共\",\"uris\":[\"/manage/mine/layer/modify\",\"/manage/mine/group/dashboard\"]},{\"name\":\"demo组\",\"uris\":[\"/manage/demo/xxpage_1\",\"/manage/system/admin/list\"]},{\"name\":\"管理员\",\"uris\":[\"/manage/system/log/list\",\"/manage/system/admin/list\"]}]', 0, '2018-12-07 08:29:00', '2019-07-19 07:21:10');
COMMIT;

-- ----------------------------
-- Table structure for group_admin
-- ----------------------------
DROP TABLE IF EXISTS `group_admin`;
CREATE TABLE `group_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组ID',
  `group_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `create_admin_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_admin_id_UNIQUE` (`group_id`,`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of group_admin
-- ----------------------------
BEGIN;
INSERT INTO `group_admin` VALUES (65, 97, 0, 0, '2019-03-25 03:24:44', '2019-03-25 03:24:44', '');
INSERT INTO `group_admin` VALUES (67, 94, 0, 0, '2019-05-02 08:30:24', '2019-05-02 08:30:24', '');
INSERT INTO `group_admin` VALUES (68, 98, 0, 0, '2019-05-02 08:39:55', '2019-05-02 08:39:55', '');
INSERT INTO `group_admin` VALUES (72, 101, 0, 0, '2019-05-09 14:50:46', '2019-05-09 14:50:46', '');
COMMIT;

-- ----------------------------
-- Table structure for group_auth
-- ----------------------------
DROP TABLE IF EXISTS `group_auth`;
CREATE TABLE `group_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `group_id` int(11) NOT NULL,
  `uri` varchar(127) NOT NULL,
  `create_admin_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_auth_uri_UNIQUE` (`group_id`,`uri`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=386 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of group_auth
-- ----------------------------
BEGIN;
INSERT INTO `group_auth` VALUES (346, 97, '/api/manage/mine/group/admin/add', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (347, 97, '/api/manage/mine/group/auth/set', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (348, 97, '/api/manage/mine/group/del', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (349, 97, '/api/manage/system/admin/add', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (350, 97, '/api/manage/system/admin/del', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (351, 97, '/api/manage/system/organize/add', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (352, 97, '/api/manage/system/organize/del', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (353, 97, '/api/manage/system/organize/edit', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (354, 97, '/manage/demo/xxpage_1', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (355, 97, '/manage/login', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (356, 97, '/manage/mine/group/admin', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (357, 97, '/manage/mine/group/auth', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (358, 97, '/manage/system/admin/list', 0, '2019-05-02 08:31:05', '2019-05-02 08:31:05', '');
INSERT INTO `group_auth` VALUES (360, 95, '/api/manage/system/admin/change_org', 0, '2019-05-02 08:50:24', '2019-05-02 08:50:24', '');
INSERT INTO `group_auth` VALUES (361, 95, '/api/manage/system/organize/add', 0, '2019-05-02 08:50:24', '2019-05-02 08:50:24', '');
INSERT INTO `group_auth` VALUES (362, 95, '/api/manage/system/organize/del', 0, '2019-05-02 08:50:24', '2019-05-02 08:50:24', '');
INSERT INTO `group_auth` VALUES (363, 95, '/api/manage/system/organize/edit', 0, '2019-05-02 08:50:24', '2019-05-02 08:50:24', '');
INSERT INTO `group_auth` VALUES (364, 95, '/manage/system/organize/list', 0, '2019-05-02 08:50:24', '2019-05-02 08:50:24', '');
INSERT INTO `group_auth` VALUES (365, 98, '/api/manage/demo/xxapi_1', 0, '2019-05-02 08:51:26', '2019-05-02 08:51:26', '');
INSERT INTO `group_auth` VALUES (366, 98, '/api/manage/mine/group/admin/remove', 0, '2019-05-02 08:51:26', '2019-05-02 08:51:26', '');
INSERT INTO `group_auth` VALUES (367, 98, '/api/manage/mine/group/edit', 0, '2019-05-02 08:51:26', '2019-05-02 08:51:26', '');
INSERT INTO `group_auth` VALUES (368, 98, '/api/manage/system/admin/del', 0, '2019-05-02 08:51:26', '2019-05-02 08:51:26', '');
INSERT INTO `group_auth` VALUES (369, 98, '/api/manage/system/admin/edit', 0, '2019-05-02 08:51:26', '2019-05-02 08:51:26', '');
INSERT INTO `group_auth` VALUES (370, 94, '/api/manage/mine/group/add', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (371, 94, '/api/manage/mine/group/admin/add', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (372, 94, '/api/manage/mine/group/admin/remove', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (373, 94, '/api/manage/mine/group/auth/set', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (374, 94, '/api/manage/mine/group/del', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (375, 94, '/api/manage/mine/group/edit', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (376, 94, '/api/manage/system/admin/add', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (377, 94, '/api/manage/system/admin/change_org', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (378, 94, '/api/manage/system/admin/del', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (379, 94, '/api/manage/system/admin/edit', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (380, 94, '/manage/mine/group/admin', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (381, 94, '/manage/mine/group/auth', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (382, 94, '/manage/system/admin/list', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (383, 94, '/manage/system/log/list', 0, '2019-05-09 03:31:54', '2019-05-09 03:31:54', '');
INSERT INTO `group_auth` VALUES (385, 101, '/api/manage/demo/xxapi_1', 0, '2019-05-09 14:51:18', '2019-05-09 14:51:18', '');
COMMIT;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(20) NOT NULL COMMENT '管理员组名称',
  `create_admin_id` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COMMENT='管理员组表';

-- ----------------------------
-- Records of groups
-- ----------------------------
BEGIN;
INSERT INTO `groups` VALUES (94, '管理员', 0, '2019-03-12 09:40:11', '2019-03-12 10:17:36', '管理员基本功能');
INSERT INTO `groups` VALUES (95, '组织管理员', 0, '2019-03-12 10:17:51', '2019-03-12 10:17:51', '管理组织');
INSERT INTO `groups` VALUES (97, 'demo组', 0, '2019-03-12 10:24:16', '2019-03-12 10:24:16', '111');
INSERT INTO `groups` VALUES (98, '公共管理', 0, '2019-05-02 08:31:39', '2019-05-02 08:31:39', '公共管理者');
INSERT INTO `groups` VALUES (100, '权限组', 0, '2019-05-09 04:15:16', '2019-05-09 04:15:16', '111');
INSERT INTO `groups` VALUES (101, 'aa组', 0, '2019-05-09 14:50:32', '2019-05-09 14:50:32', 'aa组');
COMMIT;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增日志id',
  `organize_id` int(11) DEFAULT NULL,
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `admin_name` varchar(64) NOT NULL,
  `authj_uri` varchar(127) NOT NULL COMMENT '记录uri',
  `authj_name` varchar(127) NOT NULL COMMENT '记录接口名称',
  `paramers` text NOT NULL COMMENT '参数',
  `ip` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `admin_id_KEY` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for organizes
-- ----------------------------
DROP TABLE IF EXISTS `organizes`;
CREATE TABLE `organizes` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织Id',
  `name` varchar(64) NOT NULL DEFAULT '',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:初始状态 1:正常使用 -1:冻结',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_KEY` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of organizes
-- ----------------------------
BEGIN;
INSERT INTO `organizes` VALUES (0, 'root', 1, '总组织', '2019-03-07 10:31:58', '2019-03-11 09:25:02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
```
