package com.acooly.module.redpack;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuq
 */
public class RedPackComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
    	
    	/** 升级扩展字段 */
		setPropertyIfMissing("acooly.ds.dbPatchs.red_red_pack_order[0].columnName", "data_map");
		setPropertyIfMissing("acooly.ds.dbPatchs.red_red_pack_order[0].patchSql",
				"ALTER TABLE `red_red_pack_order` ADD COLUMN `data_map` VARCHAR(512) NULL COMMENT '业务扩展';");

    }
}
