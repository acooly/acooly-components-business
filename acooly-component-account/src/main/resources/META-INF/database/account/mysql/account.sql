CREATE TABLE `ac_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account_no` varchar(64) NOT NULL COMMENT '账户编码',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID，外部集成环境用户/客户标志，可选提高性能',
  `user_no` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
  `account_type` varchar(32) NOT NULL COMMENT '账户类型',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `balance` bigint(20) NOT NULL DEFAULT '0' COMMENT '余额',
  `freeze` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额',
  `status` varchar(16) NOT NULL COMMENT '状态 {enable:正常, pause:暂停,disable:禁用}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USER_ID_ACCOUNT_TYPE` (`user_id`,`account_type`),
  KEY `UK_USER_NO_ACCOUNT_TYPE` (`user_no`,`account_type`),
  KEY `UK_ACCOUNT_NO` (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户信息';

CREATE TABLE `ac_account_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bill_no` varchar(32) NOT NULL DEFAULT '' COMMENT '交易编码',
  `account_id` bigint(20) NOT NULL COMMENT '账户ID',
  `account_no` varchar(64) NOT NULL DEFAULT '' COMMENT '账户编码',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_no` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
  `username` varchar(64) DEFAULT '' COMMENT '用户名(冗余)',
  `merch_order_no` varchar(64) DEFAULT NULL COMMENT '外部订单号',
  `biz_order_no` varchar(64) DEFAULT NULL COMMENT '内部订单号',
  `amount` bigint(20) NOT NULL COMMENT '交易金额',
  `balance_post` bigint(20) NOT NULL COMMENT '变动后余额',
  `freeze_amount` bigint(20) NOT NULL COMMENT '冻结金额',
  `freeze_post` bigint(20) NOT NULL COMMENT '冻结后总额',
  `direction` varchar(16) NOT NULL COMMENT '资金流向 {in:入金,out:出金,keep:不变}',
  `trade_code` varchar(32) NOT NULL COMMENT '交易码 (接口扩展点)',
  `busi_id` bigint(20) DEFAULT NULL COMMENT '相关业务ID',
  `busi_data` varchar(128) DEFAULT NULL COMMENT '相关业务数据',
  `batch_no` varchar(64) DEFAULT NULL COMMENT '批量交易号',
  `status` varchar(16) NOT NULL COMMENT '状态{enable:有效,disable:无效}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  `account_type` varchar(64) DEFAULT NULL COMMENT '账务用户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户进出账';

CREATE TABLE `ac_account_trade_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_code` varchar(16) DEFAULT NULL COMMENT '交易编码',
  `trade_name` varchar(32) DEFAULT NULL COMMENT '交易名称',
  `direction` varchar(16) DEFAULT NULL COMMENT '方向',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义交易码';
