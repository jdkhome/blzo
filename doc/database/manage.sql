/*
 Navicat Premium Data Transfer

 Source Server         : jdkhome
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : data-public.jdkhome.com:3306
 Source Schema         : blzo

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 04/12/2018 16:33:38
*/

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
  `phone` varchar(20) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:初始状态 1:正常使用 -1:冻结',
  `last_ip` varchar(20) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `username_KEY` (`username`) USING BTREE,
  KEY `phone_KEY` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admins
-- ----------------------------
BEGIN;
INSERT INTO `admins` VALUES (9, 'linkji', 'jdk', 'ac6929a38147e42dc45887af5c5ebe80', '5162fdb64b91496f9402ce6046d11174', '10086', 0, '0:0:0:0:0:0:0:1', '2018-12-04 16:33:13', '测试', '2018-05-29 04:06:03', '2018-12-04 08:33:22');
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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of group_admin
-- ----------------------------
BEGIN;
INSERT INTO `group_admin` VALUES (10, 61, 9, 9, '2018-04-19 07:57:25', '2018-05-29 08:43:21', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of group_auth
-- ----------------------------
BEGIN;
INSERT INTO `group_auth` VALUES (103, 61, '/api/manage/mine/group/menu/add', 3, '2018-04-19 07:59:18', '2018-04-19 07:59:18', '');
INSERT INTO `group_auth` VALUES (104, 61, '/api/manage/mine/group/auth/remove', 3, '2018-04-19 07:59:20', '2018-04-19 07:59:20', '');
INSERT INTO `group_auth` VALUES (105, 61, '/manage/mine/admin/edit', 3, '2018-04-19 07:59:22', '2018-04-19 07:59:22', '');
INSERT INTO `group_auth` VALUES (106, 61, '/api/manage/mine/group/admin/add', 3, '2018-04-19 07:59:24', '2018-04-19 07:59:24', '');
INSERT INTO `group_auth` VALUES (107, 61, '/api/manage/mine/group/del', 3, '2018-04-19 07:59:26', '2018-04-19 07:59:26', '');
INSERT INTO `group_auth` VALUES (109, 61, '/api/manage/mine/admin/del', 3, '2018-04-19 07:59:29', '2018-04-19 07:59:29', '');
INSERT INTO `group_auth` VALUES (110, 61, '/manage/mine/group/admin', 3, '2018-04-19 07:59:32', '2018-04-19 07:59:32', '');
INSERT INTO `group_auth` VALUES (111, 61, '/api/manage/mine/group/auth/add', 3, '2018-04-19 07:59:34', '2018-04-19 07:59:34', '');
INSERT INTO `group_auth` VALUES (112, 61, '/manage/mine/group/add', 3, '2018-04-19 07:59:35', '2018-04-19 07:59:35', '');
INSERT INTO `group_auth` VALUES (113, 61, '/api/manage/mine/group/menu/remove', 3, '2018-04-19 07:59:36', '2018-04-19 07:59:36', '');
INSERT INTO `group_auth` VALUES (114, 61, '/manage/mine/admin/add', 3, '2018-04-19 07:59:37', '2018-04-19 07:59:37', '');
INSERT INTO `group_auth` VALUES (117, 61, '/api/manage/mine/admin/edit', 3, '2018-04-19 07:59:39', '2018-04-19 07:59:39', '');
INSERT INTO `group_auth` VALUES (118, 61, '/manage/mine/admin/list', 3, '2018-04-19 07:59:39', '2018-04-19 07:59:39', '');
INSERT INTO `group_auth` VALUES (119, 61, '/manage/mine/group/auth', 3, '2018-04-19 07:59:39', '2018-04-19 07:59:39', '');
INSERT INTO `group_auth` VALUES (120, 61, '/api/manage/mine/group/edit', 3, '2018-04-19 07:59:41', '2018-04-19 07:59:41', '');
INSERT INTO `group_auth` VALUES (121, 61, '/api/manage/mine/group/admin/remove', 3, '2018-04-19 07:59:42', '2018-04-19 07:59:42', '');
INSERT INTO `group_auth` VALUES (122, 61, '/manage/mine/group/menu', 3, '2018-04-19 07:59:42', '2018-04-19 07:59:42', '');
INSERT INTO `group_auth` VALUES (123, 61, '/api/manage/mine/group/add', 3, '2018-04-19 07:59:45', '2018-04-19 07:59:45', '');
INSERT INTO `group_auth` VALUES (125, 61, '/manage/mine/group/edit', 3, '2018-04-19 07:59:45', '2018-04-19 07:59:45', '');
INSERT INTO `group_auth` VALUES (126, 61, '/manage/mine/group/list', 3, '2018-04-19 07:59:47', '2018-04-19 07:59:47', '');
INSERT INTO `group_auth` VALUES (130, 61, '/api/manage/mine/admin/add', 3, '2018-04-19 09:57:21', '2018-04-19 09:57:21', '');
INSERT INTO `group_auth` VALUES (133, 61, '/manage/mine/log/list', 3, '2018-04-20 06:52:42', '2018-04-20 06:52:42', '');
COMMIT;

-- ----------------------------
-- Table structure for group_menu
-- ----------------------------
DROP TABLE IF EXISTS `group_menu`;
CREATE TABLE `group_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `group_id` int(11) NOT NULL,
  `uri` varchar(127) NOT NULL,
  `create_admin_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_menu_uri_UNIQUE` (`group_id`,`uri`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of group_menu
-- ----------------------------
BEGIN;
INSERT INTO `group_menu` VALUES (24, 61, '/manage/mine/admin/list', 3, '2018-04-19 07:57:33', '2018-04-19 07:57:33', '');
INSERT INTO `group_menu` VALUES (25, 61, '/manage/mine/group/list', 3, '2018-04-19 07:57:36', '2018-04-19 07:57:36', '');
INSERT INTO `group_menu` VALUES (32, 61, '/manage/mine/log/list', 3, '2018-04-20 06:52:35', '2018-04-20 06:52:35', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COMMENT='管理员组表';

-- ----------------------------
-- Records of groups
-- ----------------------------
BEGIN;
INSERT INTO `groups` VALUES (61, '管理员组', 3, '2018-04-19 07:57:12', '2018-04-19 07:57:12', '管理');
COMMIT;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增日志id',
  `admin_id` int(11) NOT NULL COMMENT '管理员id',
  `admin_name` varchar(64) NOT NULL,
  `authj_uri` varchar(127) NOT NULL COMMENT '记录uri',
  `authj_name` varchar(127) NOT NULL COMMENT '记录接口名称',
  `paramers` text NOT NULL COMMENT '参数',
  `ip` varchar(20) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `admin_id_KEY` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4536 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of logs
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
