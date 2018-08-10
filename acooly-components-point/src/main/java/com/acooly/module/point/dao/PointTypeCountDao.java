/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-06
 */
package com.acooly.module.point.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.point.entity.PointTypeCount;

/**
 * 积分业务统计 Mybatis Dao
 *
 * Date: 2018-08-06 16:18:40
 * 
 * @author acooly
 */
public interface PointTypeCountDao extends EntityMybatisDao<PointTypeCount> {

	@Select("select * from point_type_count where user_name=#{userName} and busi_type=#{busiType} for update")
	PointTypeCount lockUserNameAndBusiType(@Param("userName") String userName, @Param("busiType") String busiType);

}
