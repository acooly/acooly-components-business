/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-02-22
 */
package com.acooly.module.point.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.point.entity.PointRule;

/**
 * 积分规则配置 Mybatis Dao
 *
 * Date: 2022-02-22 17:32:57
 * 
 * @author acooly
 */
public interface PointRuleDao extends EntityMybatisDao<PointRule> {

	@Select("select * from pt_point_rule where busi_type_code=#{busiTypeCode} and status='enable'")
	PointRule findByBusiTypeCode(@Param("busiTypeCode") String busiTypeCode);

}
