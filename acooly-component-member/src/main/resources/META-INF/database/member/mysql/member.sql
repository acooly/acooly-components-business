CREATE TABLE `b_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `parentid` bigint(20) DEFAULT NULL,
  `parent_user_no` varchar(64) DEFAULT NULL,
  `user_no` varchar(64) NOT NULL COMMENT '会员编码',
  `path` varchar(255) NOT NULL COMMENT '路径',
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(256) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `mobile_no` varchar(16) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `real_name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `cert_no` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `status` varchar(16) DEFAULT NULL,
  `user_type` varchar(16) DEFAULT NULL,
  `busi_type` varchar(16) DEFAULT NULL,
  `registry_source` varchar(32) DEFAULT NULL,
  `grade` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_MEMBER_USERNO` (`user_no`),
  UNIQUE KEY `UK_MEMBER_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='会员信息';

CREATE TABLE `b_member_contact` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_no` varchar(64) NOT NULL COMMENT '用户编码',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `mobile_no` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `phone_no` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(16) DEFAULT NULL COMMENT 'QQ账号',
  `wangwang` varchar(32) DEFAULT NULL COMMENT '旺旺账号',
  `wechat` varchar(32) DEFAULT NULL COMMENT '微信账号',
  `facebeek` varchar(32) DEFAULT NULL COMMENT '脸书账号',
  `google` varchar(32) DEFAULT NULL COMMENT '谷歌账号',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `district` varchar(32) DEFAULT NULL COMMENT '区',
  `zip` varchar(16) DEFAULT NULL COMMENT '邮政编码',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名',
  `career` varchar(255) DEFAULT NULL COMMENT '职业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员联系信息';

CREATE TABLE `b_member_enterprise` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_no` varchar(64) NOT NULL COMMENT '用户编码',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `ent_type` varchar(16) DEFAULT NULL COMMENT '企业类型 {company:公司,selfemp:个体户}',
  `ent_name` varchar(64) DEFAULT NULL COMMENT '企业名称',
  `licence_no` varchar(64) DEFAULT NULL COMMENT '社会统一信用代码',
  `licence_path` varchar(512) DEFAULT NULL COMMENT '营业执照图片地址',
  `licence_address` varchar(128) DEFAULT NULL COMMENT '营业执照地址',
  `licence_expire_time` varchar(128) DEFAULT NULL COMMENT '营业执照截止日期',
  `ent_address` varchar(128) DEFAULT NULL COMMENT '企业地址',
  `business_life` int(11) DEFAULT NULL COMMENT '营业年限',
  `legal_name` varchar(64) DEFAULT NULL COMMENT '法人姓名',
  `legal_cert_type` varchar(64) DEFAULT 'ID' COMMENT '法人证件类型: 默认身份证',
  `legal_cert_no` varchar(64) DEFAULT NULL COMMENT '法人证件号码',
  `legal_cert_valid_time` varchar(32) DEFAULT NULL COMMENT '法人证件到期时间',
  `legal_cert_front_path` varchar(512) DEFAULT NULL COMMENT '法人证件正面图片',
  `legal_cert_back_path` varchar(512) DEFAULT NULL COMMENT '法人证件背面图片',
  `business_scope` varchar(512) DEFAULT NULL COMMENT '经营范围',
  `holding_enum` varchar(32) DEFAULT 'BUSINESS' COMMENT '实际控股人或企业类型{PERSON:个人,BUSINESS:企业,INDIVIDUAL:个体户,BUSINESS:企业用户,MIDDLE_ACCOUNT:中间账户,OPERATOR:运营商}',
  `holding_name` varchar(64) DEFAULT NULL COMMENT '股东或实际控制人真实姓名',
  `holding_cert_type` varchar(64) DEFAULT 'ID' COMMENT '股东或实际控制人证件类型{ID:身份证}',
  `holding_cert_no` varchar(64) DEFAULT NULL COMMENT '股东或实际控制人证件号',
  `holding_cert_valid_time` varchar(32) DEFAULT NULL COMMENT '股东或实际控制人证件到期时间',
  `holding_cert_front_path` varchar(512) DEFAULT NULL COMMENT '股东或实际控制人证件正面图片',
  `holding_cert_back_path` varchar(512) DEFAULT NULL COMMENT '股东或实际控制人证件背面图片',
  `organization_code` varchar(32) DEFAULT NULL COMMENT '组织机构代码',
  `organization_path` varchar(512) DEFAULT NULL COMMENT '组织机构代码证',
  `account_license_no` varchar(32) DEFAULT NULL COMMENT '开户许可证号码',
  `account_license_path` varchar(512) DEFAULT NULL COMMENT '开户许可证图片',
  `tax_authority_no` varchar(32) DEFAULT NULL COMMENT '税务登记证号码',
  `tax_authority_path` varchar(512) DEFAULT NULL COMMENT '税务登记证图片',
  `cert_status` varchar(16) DEFAULT NULL COMMENT '认证状态',
  `website` varchar(128) DEFAULT NULL COMMENT '网站',
  `profile` varchar(512) DEFAULT NULL COMMENT '简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `comments` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id` (`user_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业会员信息';

CREATE TABLE `b_member_personal` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_no` varchar(255) NOT NULL COMMENT '用户编码',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `cert_type` varchar(16) DEFAULT NULL COMMENT '证件类型',
  `cert_no` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `cert_validity_date` datetime DEFAULT NULL COMMENT '证件有效期',
  `cert_front_path` varchar(255) DEFAULT NULL COMMENT '证件正面照片',
  `cert_back_path` varchar(255) DEFAULT NULL COMMENT '证件背面照片',
  `cert_hold_path` varchar(255) DEFAULT NULL COMMENT '证件手持照片',
  `cert_status` varchar(16) DEFAULT NULL COMMENT '认证状态',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `gender` varchar(16) DEFAULT NULL COMMENT '性别',
  `car_status` varchar(255) DEFAULT NULL COMMENT '婚姻状况 {married:已婚, unmarried:未婚}',
  `children_count` int(11) NOT NULL COMMENT '子女情况',
  `education_level` varchar(255) DEFAULT NULL COMMENT '教育背景(学历) {primary:小学,middle:中学,college:大专,university:本科,master:硕士,doctor:博士,other:其他}',
  `house_fund` varchar(255) DEFAULT NULL COMMENT '公积金 {yes:有,no:无}',
  `house_statue` varchar(255) DEFAULT NULL COMMENT '住房情况 {rental:租房,own:自有,other:其他}',
  `income_month` varchar(255) DEFAULT NULL COMMENT '月收入 {below3k:3000以下, bt3to5K:3000-5000(含),bt5Kto1W:5000-10000(含),bt1Wto2W:10000-20000(含),bt2Wto5W:20000-50000,above5W:50000以上}',
  `marital_status` varchar(255) DEFAULT NULL COMMENT '婚姻状况 {married:已婚, unmarried:未婚}',
  `social_insurance` varchar(255) DEFAULT NULL COMMENT '社会保险 {yes:有,no:无}',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人会员信息';

CREATE TABLE `b_member_profile` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_no` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `daily_words` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `profile_photo_type` varchar(16) DEFAULT NULL COMMENT '头像类型 {builtIn:内置,upload:上传,outside:外部}',
  `profile_photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `email_status` varchar(16) DEFAULT NULL COMMENT '邮箱认证 {yes:已认证,no:未认证}',
  `mobile_no_status` varchar(16) DEFAULT NULL COMMENT '手机认证 {yes:已认证,no:未认证}',
  `real_name_status` varchar(16) DEFAULT NULL COMMENT '实名认证 {yes:已认证,no:未认证}',
  `secret_qa_status` varchar(16) DEFAULT NULL COMMENT '安全问题设置状态',
  `sms_send_status` varchar(16) DEFAULT NULL COMMENT '发送短信 {yes:发送,no:不发送}',
  `manager` varchar(32) DEFAULT NULL COMMENT '客户经理',
  `broker` varchar(32) DEFAULT NULL COMMENT '经纪人',
  `inviter` varchar(32) DEFAULT NULL COMMENT '邀请人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员配置信息';

CREATE TABLE `b_member_secret_qa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `user_no` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `question_code` varchar(16) DEFAULT NULL COMMENT '问题编码',
  `question` varchar(255) DEFAULT NULL COMMENT '问题',
  `answer` varchar(255) DEFAULT NULL COMMENT '答案',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `comments` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
