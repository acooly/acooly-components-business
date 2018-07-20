CREATE TABLE `b_member` (
  `ID` bigint(20) NOT NULL COMMENT '会员ID',
  `user_no` varchar(64) NOT NULL COMMENT '会员编码',
  `user_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `mobile_no` varchar(16) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `real_name` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息';
