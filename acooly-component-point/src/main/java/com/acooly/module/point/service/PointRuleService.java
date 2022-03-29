/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-02-22
 *
 */
package com.acooly.module.point.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.point.entity.PointRule;

/**
 * 积分规则配置 Service接口
 *
 * Date: 2022-02-22 17:32:57
 * 
 * @author acooly
 *
 */
public interface PointRuleService extends EntityService<PointRule> {

	PointRule findByBusiTypeCode(String busiTypeCode);

}
