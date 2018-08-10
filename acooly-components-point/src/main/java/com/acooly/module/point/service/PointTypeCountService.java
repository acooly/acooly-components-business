/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-06
 *
 */
package com.acooly.module.point.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.point.domain.PointTrade;
import com.acooly.module.point.entity.PointTypeCount;

/**
 * 积分业务统计 Service接口
 *
 * Date: 2018-08-06 16:18:40
 * @author acooly
 *
 */
public interface PointTypeCountService extends EntityService<PointTypeCount> {

	void savePointTypeCount(PointTrade pointTrade);

}
