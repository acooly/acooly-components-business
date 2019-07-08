/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
 package com.acooly.module.countnum.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.mybatis.EntityMybatisDao;

/**
 * 游戏-计数 Mybatis Dao
 *
 * Date: 2019-07-03 11:48:59
 * @author cuifuq
 */
public interface CountNumDao extends EntityMybatisDao<CountNum> {

	
	@Select("select * from game_count_num where id = #{id} for update")
	CountNum lockById(@Param("id") Long id);
	
}
