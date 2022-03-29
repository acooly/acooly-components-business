/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.point.entity.PointTrade;

/**
 * 积分交易信息 Mybatis Dao
 * <p>
 * <p>
 * Date: 2017-02-03 22:50:14
 *
 * @author cuifuqiang
 */
public interface PointTradeDao extends EntityMybatisDao<PointTrade> {

	@Select("select coalesce(sum(amount),0) as point from pt_point_trade where user_no=#{userNo} " //
			+ "and create_time>=#{startTime} and create_time<=#{endTime} and trade_type ='produce'")
	Long getProducePoint(@Param("userNo") String userNo, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	@Select("select coalesce(sum(amount),0) as point from pt_point_trade where user_no=#{userNo} " //
			+ "and create_time>=#{startTime} and create_time<=#{endTime} and trade_type ='expense'")
	Long getExpensePoint(@Param("userNo") String userNo, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	@Select("select coalesce(sum(amount),0) as point from pt_point_trade where trade_type ='produce' and "//
			+ " user_no=#{userNo} and  busi_id=#{busiId}  and busi_type=#{busiType} GROUP BY user_no")
	Long countProducePointByBusi(@Param("userNo") String userNo, @Param("busiId") String busiId,
			@Param("busiType") String busiType);

	@Select("select * from pt_point_trade where trade_type =#{tradeType} and user_no=#{userNo} and  busi_id=#{busiId}  and busi_type=#{busiType} ")
	PointTrade findByUserNoAndBusiIdAndBusiType(@Param("tradeType") String tradeType, @Param("userNo") String userNo,
			@Param("busiId") String busiId, @Param("busiType") String busiType);

}
