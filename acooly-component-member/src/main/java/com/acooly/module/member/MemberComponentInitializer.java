/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-18 00:37 创建
 */
package com.acooly.module.member;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author acooly
 * @date 2018-07-10
 */
public class MemberComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing("acooly.ds.dbPatchs.b_member_contact[0].columnName", "company_name");
        setPropertyIfMissing("acooly.ds.dbPatchs.b_member_contact[0].patchSql", "ALTER TABLE `b_member_contact` ADD COLUMN `company_name` VARCHAR(255) NULL COMMENT '公司名';");

        setPropertyIfMissing("acooly.ds.dbPatchs.b_member_contact[1].columnName", "career");
        setPropertyIfMissing("acooly.ds.dbPatchs.b_member_contact[1].patchSql", "ALTER TABLE `b_member_contact` ADD COLUMN `career` VARCHAR(255) NULL COMMENT '职业';");

        /**
         * 升级member增加path
         */
        setPropertyIfMissing("acooly.ds.dbPatchs.b_member[0].columnName", "path");
        setPropertyIfMissing("acooly.ds.dbPatchs.b_member[0].patchSql", "ALTER TABLE `b_member` ADD COLUMN `path` VARCHAR(255) NULL " +
                "COMMENT '搜索路径（/id/id/）';");

    }
}
