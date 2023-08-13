INSERT INTO `sys_resource` (`ID`, `PARENTID`, `NAME`, `TYPE`, `SHOW_STATE`, `ORDER_TIME`, `VALUE`, `SHOW_MODE`, `ICON`, `DESCN`, `create_time`, `update_time`)
VALUES
	(2018073029, NULL, '账务组件', 'MENU', 0, '2018-06-18 15:53:37', '', 1, 'fa fa-jpy', NULL, '2018-06-18 15:53:37', '2018-06-18 15:53:37'),
	(2018073030, 2018073029, '交易码管理', 'URL', 0, '2018-06-18 15:54:22', '/manage/component/account/accountTradeCode/index.html', 1, 'fa fa-circle-o', NULL, '2018-06-18 15:54:22', '2018-06-18 15:55:25'),
	(2018073031, 2018073029, '账户管理', 'URL', 0, '2018-07-03 22:48:19', '/manage/component/account/account/index.html', 1, 'fa fa-circle-o', NULL, '2018-07-03 22:47:42', '2018-07-20 00:48:27'),
	(2018073032, 2018073029, '账户流水', 'URL', 0, '2018-07-03 22:48:18', '/manage/component/account/accountBill/index.html', 1, 'fa fa-circle-o', NULL, '2018-07-03 22:48:18', '2018-07-03 22:48:18');
INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`) VALUES (20191073031, 2018073031, '调账', 'FUNCTION', 1, '2022-09-26 09:40:05', 'function_account:handleAccount', 1, 'fa-circle-o', NULL, '2022-09-26 09:40:05', '2022-09-26 12:01:10');
INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`) VALUES (20191073032, 2018073031, '开户', 'FUNCTION', 1, '2022-09-26 12:00:21', 'function_account:createAccount', 1, 'fa-circle-o', NULL, '2022-09-26 12:00:21', '2022-09-26 12:01:53');


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`)
VALUES
	(1, 2018073029),
	(1, 2018073030),
	(1, 2018073031),
    (1, 2018073032),
    (1, 20191073031),
    (1, 20191073032);
