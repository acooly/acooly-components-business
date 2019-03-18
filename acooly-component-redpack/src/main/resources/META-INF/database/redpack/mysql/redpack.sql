/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : acooly

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-03-14 15:18:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `red_red_pack`
-- ----------------------------
DROP TABLE IF EXISTS `red_red_pack`;
CREATE TABLE `red_red_pack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) NOT NULL COMMENT '名称',
  `send_user_id` bigint(20) NOT NULL COMMENT '用户id',
  `send_user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `status` varchar(32) NOT NULL COMMENT '状态{init:初始化,processing:支付中,refunding:退款中,refund_success:退款完结,success:正常完结}',
  `overdue_time` datetime DEFAULT NULL COMMENT '过期时间',
  `total_amount` bigint(20) DEFAULT '0' COMMENT '红包总金额',
  `send_out_amount` bigint(20) DEFAULT '0' COMMENT '已发送金额',
  `refund_amount` bigint(20) DEFAULT '0' COMMENT '退款金额',
  `total_num` bigint(20) DEFAULT '0' COMMENT '总数',
  `send_out_num` bigint(20) DEFAULT '0' COMMENT '已发送数量',
  `partake_num` bigint(20) DEFAULT '0' COMMENT '参与次数(0:无限次)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(64) DEFAULT NULL COMMENT '红包备注',
  `business_id` varchar(64) DEFAULT NULL COMMENT '业务id',
  `business_data` varchar(255) DEFAULT NULL COMMENT '业务参数',
  `comments` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包';


-- ----------------------------
-- Table structure for `red_red_pack_order`
-- ----------------------------
DROP TABLE IF EXISTS `red_red_pack_order`;
CREATE TABLE `red_red_pack_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `red_pack_id` bigint(20) NOT NULL COMMENT '红包id',
  `red_pack_title` varchar(64) DEFAULT NULL COMMENT '红包名称',
  `send_user_id` bigint(20) DEFAULT NULL COMMENT '红包发送者id',
  `send_user_name` varchar(64) DEFAULT NULL COMMENT '红包发送者名称',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `is_first` varchar(16) DEFAULT 'NO' COMMENT '是否第一名',
  `type` varchar(255) DEFAULT NULL COMMENT '类型{RED_PACK:红包支付,REFUND:退款支付}',
  `status` varchar(32) DEFAULT NULL COMMENT '状态{init:初始化,processing:支付中,success:支付成功,fail:支付失败}',
  `amount` bigint(20) DEFAULT '0' COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包订单';
