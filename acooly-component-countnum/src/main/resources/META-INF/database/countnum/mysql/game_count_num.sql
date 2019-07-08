/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : acooly

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-07-08 10:07:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `game_count_num`
-- ----------------------------
DROP TABLE IF EXISTS `game_count_num`;
CREATE TABLE `game_count_num` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '名称',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `create_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建者用户名',
  `type` varchar(32) DEFAULT NULL COMMENT '类型{time_limit:时间限制,num_limit:次数限制}',
  `overdue_time` datetime DEFAULT NULL COMMENT '过期时间',
  `status` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '状态{init:初始化,processing:游戏中,success:结束}',
  `is_cover` varchar(16) DEFAULT 'NO' COMMENT '是否参与覆盖{YES:是,NO:否}',
  `max_num` bigint(20) DEFAULT '0' COMMENT '最大参与人数(0:无限次)',
  `limit_num` bigint(20) DEFAULT '0' COMMENT '可参与次数(0:无限次)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `business_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务id',
  `business_data` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务参数',
  `comments` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏-计数';

-- ----------------------------
-- Records of game_count_num
-- ----------------------------

-- ----------------------------
-- Table structure for `game_count_num_order`
-- ----------------------------
DROP TABLE IF EXISTS `game_count_num_order`;
CREATE TABLE `game_count_num_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '订单号',
  `count_id` bigint(20) NOT NULL COMMENT '游戏id',
  `count_title` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '游戏名称',
  `count_type` varchar(32) DEFAULT NULL COMMENT '类型{time_limit:时间限制,num_limit:次数限制}',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建者id',
  `create_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建者用户名',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名称',
  `num` bigint(20) DEFAULT '0' COMMENT '有效值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='游戏-计数用户订单';

-- ----------------------------
-- Records of game_count_num_order
-- ----------------------------
