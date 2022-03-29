INSERT INTO `sys_resource` VALUES ('20170233', null, '组件:积分', 'URL', '0', '2017-02-03 23:59:29', '', '1', 'fa fa-star', null, '2017-02-03 23:59:29', '2017-02-03 23:59:29');
INSERT INTO `sys_resource` VALUES ('201702331', '20170233', '积分账户管理', 'URL', '0', '2017-02-04 00:01:54', '/manage/point/pointAccount/index.html', '1', 'fa fa-circle-o', null, '2017-02-04 00:00:09', '2017-02-04 00:04:42');
INSERT INTO `sys_resource` VALUES ('201702332', '20170233', '积分等级设置', 'URL', '0', '2017-02-04 00:01:55', '/manage/point/pointGrade/index.html', '1', 'fa fa-circle-o', null, '2017-02-04 00:00:07', '2017-02-04 00:04:35');
INSERT INTO `sys_resource` VALUES ('201702333', '20170233', '积分交易记录', 'URL', '0', '2017-02-04 00:01:53', '/manage/point/pointTrade/index.html', '1', 'fa fa-circle-o', null, '2017-02-04 00:01:53', '2017-02-04 00:01:53');
INSERT INTO `sys_resource` VALUES ('201702335', '20170233', '积分规则设置', 'URL', '0', '2017-02-04 00:01:52', '/manage/point/pointRule/index.html', '1', 'fa fa-circle-o', null, '2017-03-13 12:43:48', '2017-03-13 12:44:07');
INSERT INTO `sys_resource` VALUES ('201702336', '20170233', '积分统计清零', 'URL', '0', '2017-02-04 00:01:52', '/manage/point/pointStatistics/index.html', '1', 'fa fa-circle-o', null, '2017-03-13 12:43:48', '2017-03-13 12:44:07');
INSERT INTO `sys_resource` VALUES ('201702337', '20170233', '积分业务统计', 'URL', '0', '2017-02-04 00:01:40', '/manage/point/pointTypeCount/index.html', '1', 'fa fa-circle-o', null, '2017-03-14 12:43:48', '2017-03-14 12:44:07');

INSERT INTO `sys_resource` VALUES (201702331001, 201702331, '积分账户-积分发放', 'FUNCTION', 1, '2022-03-22 16:35:00', 'point:pointAccount_grant', 1, 'fa-circle-o', NULL, '2022-03-22 16:35:00', '2022-03-22 16:35:04');
INSERT INTO `sys_resource` VALUES (201702336001, 201702336, '积分统计清零操作执行', 'FUNCTION', 1, '2022-03-22 16:46:11', 'point:pointByCountAndClear', 1, 'fa-circle-o', NULL, '2022-03-22 16:46:11', '2022-03-22 16:46:11');


INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '20170233');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702331');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702332');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702333');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702335');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702336');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702337');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702331001');
INSERT INTO `sys_role_resc` (`ROLE_ID`, `RESC_ID`) VALUES ('1', '201702336001');
