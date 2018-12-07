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

 Date: 07/12/2018 16:35:33
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
  `phone` varchar(20) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态 0:初始状态 1:正常使用 -1:冻结',
  `last_ip` varchar(20) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `layer` varchar(2048) DEFAULT NULL COMMENT '菜单layer',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_KEY` (`username`) USING BTREE,
  KEY `phone_KEY` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB AUTO_INCREMENT=35
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(20) NOT NULL COMMENT '权限组名称',
  `create_admin_id` int(11) NOT NULL COMMENT '创建者ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限组表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
