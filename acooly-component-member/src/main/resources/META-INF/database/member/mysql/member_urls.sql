INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`, `create_time`, `update_time`)
VALUES
	(201808211404, NULL, '会员组件', 'MENU', 0, '2018-09-20 23:13:41', '', 1, 'icons-resource-guanyuwomen', NULL, '2018-09-20 23:13:41', '2018-09-20 23:13:41'),
	(201808211405, 201808211404, '会员信息管理', 'URL', 0, '2018-09-21 17:47:31', '/manage/component/member/member/index.html', 1, 'icons-resource-admin', NULL, '2018-09-20 23:14:14', '2018-10-10 11:50:44'),
	(201808211406, 201808211404, '会员配置信息', 'URL', 1, '2018-09-21 17:46:23', '/manage/component/member/memberProfile/index.html', 1, 'icons-resource-jiekuanhetongguanli', NULL, '2018-09-21 17:46:23', '2018-10-10 11:52:03'),
	(201808211407, 201808211404, '个人会员信息', 'URL', 0, '2018-09-21 17:46:53', '/manage/component/member/memberPersonal/index.html', 1, 'icons-resource-jingxiaoshang', NULL, '2018-09-21 17:46:53', '2018-09-21 17:46:53'),
	(201808211408, 201808211404, '企业会员信息', 'URL', 0, '2018-09-21 17:47:30', '/manage/component/member/memberEnterprise/index.html', 1, 'icons-resource-tiyanbiao', NULL, '2018-09-21 17:47:30', '2018-09-21 17:47:30');


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`)
VALUES
	(1, 201808211404),
	(1, 201808211405),
	(1, 201808211406),
	(1, 201808211407),
	(1, 201808211408),
	(1, 201808211409);
