INSERT INTO `sys_resource` (`id`, `parentid`, `name`, `type`, `show_state`, `order_time`, `value`, `show_mode`, `icon`, `descn`, `create_time`, `update_time`)
VALUES (20221120, NULL, '资产管理', 'MENU', 0, '2021-08-14 12:40:41', '', 1, 'fa-circle-o', NULL, '2021-08-14 12:40:41', '2021-08-14 12:40:41'),
       (202211201, 20221120, '资产管理', 'URL', 0, '2021-08-15 12:54:02', '/manage/assetmgmt/secretbox/index.html', 1, 'fa-circle-o', NULL, '2021-08-14 12:41:34', '2021-08-17 11:15:51'),
       (202211202, 20221120, '资产分类', 'URL', 1, '2021-08-15 12:54:01', '/manage/module/treeType/treeType/index.html?theme=secretbox', 1, 'fa-circle-o', NULL, '2021-08-15 12:54:01', '2021-08-17 11:16:40');

INSERT INTO `sys_role_resc` (`role_id`, `resc_id`)
VALUES (1, 20221120),
       (1, 202211201),
       (1, 202211202);


