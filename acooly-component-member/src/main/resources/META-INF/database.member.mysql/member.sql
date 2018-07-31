-- Create syntax for TABLE 'b_member'
CREATE TABLE `b_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `parentid` bigint(11) DEFAULT NULL COMMENT '父会员ID',
  `parent_user_no` varchar(64) DEFAULT NULL COMMENT '父会员编码',
  `user_no` varchar(64) NOT NULL COMMENT '会员编码',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(256) NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(64) NOT NULL DEFAULT '' COMMENT '密码盐',
  `user_type` int(11) NOT NULL COMMENT '用户类型{1:个人用户,2:企业用户}',
  `mobile_no` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(128) DEFAULT NULL COMMENT '邮件',
  `real_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `cert_no` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `grade` int(11) DEFAULT NULL COMMENT '用户等级 {1:普通,2:VIP}',
  `status` int(11) DEFAULT NULL COMMENT '状态 {notactive:未激活,enable:正常,disable:禁用}',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息';

-- Create syntax for TABLE 'b_member_contact'
CREATE TABLE `b_member_contact` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_no` varchar(64) NOT NULL COMMENT '会员编码',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile_no` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `phone_no` varchar(32) DEFAULT NULL COMMENT '电话号码',
  `province` varchar(64) DEFAULT NULL COMMENT '居住地 省',
  `city` varchar(64) DEFAULT NULL COMMENT '居住地 市',
  `district` varchar(64) DEFAULT NULL COMMENT '居住地 县/区',
  `address` varchar(256) DEFAULT NULL COMMENT '详细地址',
  `zip` varchar(6) DEFAULT NULL COMMENT '邮政编码',
  `qq` varchar(16) DEFAULT NULL COMMENT 'QQ',
  `wechat` varchar(32) DEFAULT NULL COMMENT 'MSN',
  `wangwang` varchar(32) DEFAULT NULL COMMENT '旺旺',
  `google` varchar(32) DEFAULT NULL COMMENT '备注',
  `facebeek` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系信息';

-- Create syntax for TABLE 'b_member_personal'
CREATE TABLE `b_member_personal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `marital_status` varchar(16) DEFAULT NULL COMMENT '婚姻状况 {married:已婚, unmarried:未婚}',
  `children_count` int(11) DEFAULT NULL COMMENT '子女情况 {0:无,1:1个,2:2个,3:3个,4:3个以上}',
  `education_level` varchar(16) DEFAULT NULL COMMENT '教育背景(学历) {primary:小学,middle:中学,college:大专,university:本科,master:硕士,doctor:博士,other:其他}',
  `income_month` varchar(16) DEFAULT NULL COMMENT '月收入 {below3k:3000以下, bt3to5K:3000-5000(含),bt5Kto1W:5000-10000(含),bt1Wto2W:10000-20000(含),bt2Wto5W:20000-50000,above5W:50000以上}',
  `social_insurance` varchar(4) DEFAULT NULL COMMENT '社会保险 {yes:有,no:无}',
  `house_fund` varchar(4) DEFAULT NULL COMMENT '公积金 {yes:有,no:无}',
  `house_statue` varchar(16) DEFAULT NULL COMMENT '住房情况 {rental:租房,own:自有,other:其他}',
  `car_status` varchar(11) DEFAULT NULL COMMENT '是否购车  {yes:有,no:无}',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员个人信息';

-- Create syntax for TABLE 'b_member_profile'
CREATE TABLE `b_member_profile` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_no` varchar(64) NOT NULL COMMENT '用户编码',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `daily_words` varchar(256) DEFAULT NULL COMMENT '个性签名',
  `profile_photo_type` varchar(16) DEFAULT NULL COMMENT '头像类型 {builtIn:内置,upload:上传,outside:外部}',
  `profile_photo` varchar(256) DEFAULT NULL COMMENT '头像',
  `real_name_status` varchar(16) DEFAULT NULL COMMENT '实名认证 {yes:已认证,no:未认证}',
  `mobile_no_status` varchar(16) DEFAULT NULL COMMENT '手机认证 {yes:已认证,no:未认证}',
  `email_status` varchar(16) DEFAULT NULL COMMENT '邮箱认证 {yes:已认证,no:未认证}',
  `sms_send_status` varchar(16) DEFAULT NULL COMMENT '发送短信 {yes:发送,no:不发送}',
  `secret_qa_status` varchar(16) DEFAULT NULL COMMENT '安全问题设置状态',
  `manager` varchar(32) DEFAULT NULL COMMENT '客户经理',
  `broker` varchar(32) DEFAULT NULL COMMENT '经纪人',
  `inviter` varchar(32) DEFAULT NULL COMMENT '邀请人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员业务信息';

-- Create syntax for TABLE 'b_member_secret_qa'
CREATE TABLE `b_member_secret_qa` (
  `id` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL COMMENT '用户ID',
  `user_no` varchar(64) NOT NULL COMMENT '用户编码',
  `question` varchar(128) NOT NULL COMMENT '问题',
  `answer` varchar(128) DEFAULT NULL COMMENT '答案',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全问题';