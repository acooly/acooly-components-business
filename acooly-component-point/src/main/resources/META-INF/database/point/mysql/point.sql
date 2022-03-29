/*
 Navicat MySQL Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1:3306
 Source Schema         : acooly

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 22/03/2022 16:48:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pt_point_type_count
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_type_count`;
CREATE TABLE `pt_point_type_count`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `num` bigint(20) NOT NULL DEFAULT 0 COMMENT '统计次数',
  `busi_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型',
  `busi_type_text` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型描述',
  `total_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '总积分',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `acc_username_idx`(`user_name`, `busi_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分业务统计' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_type_count
-- ----------------------------

-- ----------------------------
-- Table structure for pt_point_trade
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_trade`;
CREATE TABLE `pt_point_trade`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易订单号',
  `trade_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '交易类型{produce:产生,expense:消费}',
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `account_id` bigint(20) NOT NULL COMMENT '积分账户ID',
  `amount` bigint(20) NOT NULL DEFAULT 0 COMMENT '交易积分',
  `end_freeze` bigint(20) NOT NULL DEFAULT 0 COMMENT '交易后冻结积分',
  `end_balance` bigint(20) NOT NULL DEFAULT 0 COMMENT '交易后积分',
  `end_available` bigint(20) NOT NULL COMMENT '交易后有效积分',
  `end_debt_point` bigint(20) NULL DEFAULT 0 COMMENT '交易后的负债账户',
  `overdue_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '过期时间',
  `busi_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关业务id',
  `busi_type` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关业务类型',
  `busi_type_text` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关业务类型描述',
  `busi_data` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关业务数据',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_no`(`user_no`) USING BTREE,
  INDEX `trade_type`(`trade_type`, `overdue_date`) USING BTREE,
  INDEX `trade_type_2`(`trade_type`, `user_no`, `busi_id`, `busi_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分交易信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_trade
-- ----------------------------

-- ----------------------------
-- Table structure for pt_point_statistics
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_statistics`;
CREATE TABLE `pt_point_statistics`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `num` bigint(20) NOT NULL DEFAULT 0 COMMENT '统计条数',
  `total_clear_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '统计清零积分',
  `current_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零前积分',
  `current_freeze_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零前冻结积分',
  `current_total_expense_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零前消费总积分',
  `current_total_overdue_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零前过期总积分',
  `current_count_overdue_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零前统计过期总积分',
  `end_balance_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零后的积分',
  `end_total_overdue_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '清零后的过期积分',
  `real_clear_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '本次清零积分',
  `overdue_date` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '过期时间',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'init' COMMENT '状态 {init:初始化,finish:完成}',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_userNo_time`(`user_no`, `overdue_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分统计' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for pt_point_rule
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_rule`;
CREATE TABLE `pt_point_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则标题',
  `busi_type_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则编码',
  `busi_type_text` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则编码描述',
  `amount` bigint(20) NULL DEFAULT NULL COMMENT '金额(元)',
  `point` bigint(20) NULL DEFAULT NULL COMMENT '积分',
  `valid_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效期(only_one_year:\'有效一年\',next_year_over:\'次年年底\')',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'enable' COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_type_code_status`(`busi_type_code`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '积分规则配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_rule
-- ----------------------------

-- ----------------------------
-- Table structure for pt_point_grade
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_grade`;
CREATE TABLE `pt_point_grade`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `num` int(11) NOT NULL DEFAULT 1 COMMENT '等级',
  `title` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `start_point` bigint(11) NOT NULL DEFAULT 0 COMMENT '积分区间_开始',
  `end_point` bigint(11) NOT NULL DEFAULT 0 COMMENT '积分区间_结束',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ' 图标',
  `comments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分等级' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_grade
-- ----------------------------
INSERT INTO `pt_point_grade` VALUES (1, 1, '等级1', 0, 10000000, '2015-12-28 18:46:34', '2016-01-11 18:40:53', '/assets/images/grade/1.png', '');
INSERT INTO `pt_point_grade` VALUES (2, 2, '等级2', 10000001, 30000000, '2015-12-28 18:47:09', '2016-01-11 18:39:03', '/assets/images/grade/2.png', '');
INSERT INTO `pt_point_grade` VALUES (3, 3, '等级3', 30000001, 50000000, '2015-12-28 18:47:42', '2016-01-11 18:38:57', '/assets/images/grade/3.png', '');
INSERT INTO `pt_point_grade` VALUES (4, 4, '等级4', 50000001, 999999999, '2015-12-28 19:38:21', '2016-12-25 15:00:13', '/assets/images/grade/4.png', '');
INSERT INTO `pt_point_grade` VALUES (5, 5, '等级5', 100000000, 999999999, '2015-12-30 15:42:27', '2017-02-04 01:17:51', '/assets/images/grade/5.png', '');

-- ----------------------------
-- Table structure for pt_point_account
-- ----------------------------
DROP TABLE IF EXISTS `pt_point_account`;
CREATE TABLE `pt_point_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户号',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `balance` bigint(20) NOT NULL DEFAULT 0 COMMENT '积分余额',
  `freeze` bigint(20) NOT NULL DEFAULT 0 COMMENT '冻结',
  `debt_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '负债积分账户',
  `total_expense_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '总消费积分',
  `total_overdue_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '总过期积分(真实清零)',
  `total_produce_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '总产生积分',
  `count_overdue_point` bigint(20) NOT NULL DEFAULT 0 COMMENT '总过期积分(累计统计)',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 {invalid:无效,valid:有效}',
  `grade_id` int(11) NOT NULL COMMENT '用户等级',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `comments` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_no`(`user_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '积分账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pt_point_account
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
