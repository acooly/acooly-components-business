/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 */
package com.acooly.module.redpack.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.redpack.entity.RedPack;

/**
 * 红包 Mybatis Dao
 *
 * Date: 2019-03-05 18:18:08
 * 
 * @author cuifuq
 */
public interface RedPackDao extends EntityMybatisDao<RedPack> {

	@Select("select * from red_red_pack where id = #{id} for update")
	RedPack lockById(@Param("id") Long id);

}
