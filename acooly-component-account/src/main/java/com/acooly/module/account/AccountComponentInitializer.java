/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-18 00:37 创建
 */
package com.acooly.module.account;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author acooly
 * @date 2018-07-10
 */
public class AccountComponentInitializer implements ComponentInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AccountComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing("acooly.ds.dbPatchs.ac_account_bill[0].patchSql", "ALTER TABLE `ac_account_bill` ADD COLUMN `account_type` VARCHAR(64) NULL COMMENT '账务用户类型';");
    }
}
