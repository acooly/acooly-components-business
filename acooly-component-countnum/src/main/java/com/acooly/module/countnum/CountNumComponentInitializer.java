/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-03-10 17:04 创建
 */
package com.acooly.module.countnum;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuq
 */
@Slf4j
public class CountNumComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {

		/** 升级扩展字段 */
		setPropertyIfMissing("acooly.ds.dbPatchs.game_count_num_order[0].columnName", "data_map");
		setPropertyIfMissing("acooly.ds.dbPatchs.game_count_num_order[0].patchSql",
				"ALTER TABLE `game_count_num_order` ADD COLUMN `data_map` VARCHAR(512) NULL COMMENT '业务扩展';");

		/** 新增 排序因子2 */
		setPropertyIfMissing("acooly.ds.dbPatchs.game_count_num_order[1].columnName", "time");
		setPropertyIfMissing("acooly.ds.dbPatchs.game_count_num_order[1].patchSql",
				"ALTER TABLE `game_count_num_order` ADD COLUMN `time`  bigint NULL DEFAULT 0 COMMENT '有效值-排序因子2' AFTER `num`;");
	}
}
