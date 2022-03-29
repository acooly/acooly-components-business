/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-13
 */
package com.acooly.module.point.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.point.entity.PointStatistics;

/**
 * 积分统计 Mybatis Dao
 *
 * <p>
 * Date: 2017-03-13 11:51:10
 *
 * @author acooly
 */
public interface PointStatisticsDao extends EntityMybatisDao<PointStatistics> {

	@Select("select * from pt_point_statistics where id=#{id} for update")
	PointStatistics lockById(@Param("id") long id);
}
