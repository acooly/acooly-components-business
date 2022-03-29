/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-02-22
 */
package com.acooly.module.point.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.point.service.PointRuleService;
import com.acooly.module.point.dao.PointRuleDao;
import com.acooly.module.point.entity.PointRule;

/**
 * 积分规则配置 Service实现
 *
 * Date: 2022-02-22 17:32:57
 *
 * @author acooly
 *
 */
@Service("pointRuleService")
public class PointRuleServiceImpl extends EntityServiceImpl<PointRule, PointRuleDao> implements PointRuleService {

	@Override
	public PointRule findByBusiTypeCode(String busiTypeCode) {
		return getEntityDao().findByBusiTypeCode(busiTypeCode);
	}

}
