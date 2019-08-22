/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-07-03
 */
package com.acooly.module.countnum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.acooly.module.mybatis.EntityMybatisDao;

/**
 * 游戏-计数用户订单 Mybatis Dao
 *
 * Date: 2019-07-03 11:48:59
 * 
 * @author cuifuq
 */
public interface CountNumOrderDao extends EntityMybatisDao<CountNumOrder> {

	
	/**
	 * 
	 * @param userId
	 * @param countNumId
	 * @return
	 */
	@Select("SELECT * FROM game_count_num_order where 1=1 and count_id=#{countNumId} and user_id=#{userId} for update")
	CountNumOrder lockByUserIdAndCountId(@Param("userId") Long userId, @Param("countNumId") Long countNumId);

	/**
	 * 时间限制类排名
	 * 
	 * @param countNumId
	 * @param limitNum
	 * @return
	 */
	@Select("SELECT * FROM game_count_num_order where 1=1 and count_id=#{countNumId} and num>0 ORDER BY num desc,valid_time asc LIMIT #{limitNum}")
	List<CountNumOrder> findByCountNumIdAndLimitByTime(@Param("countNumId") Long countNumId,
			@Param("limitNum") Long limitNum);

	/**
	 * 数量限制类排名
	 * 
	 * @param countNumId
	 * @param limitNum
	 * @return
	 */
	@Select("SELECT * FROM game_count_num_order where 1=1 and count_id=#{countNumId} and num>0 ORDER BY num asc,valid_time asc LIMIT #{limitNum}")
	List<CountNumOrder> findByCountNumIdAndLimitByNum(@Param("countNumId") Long countNumId,
			@Param("limitNum") Long limitNum);

	@Select("SELECT * FROM game_count_num_order where 1=1 and count_id=#{countNumId} and user_id=#{userId}")
	List<CountNumOrder> findByCountNumIdAndUserId(@Param("countNumId") long countNumId, @Param("userId") long userId);
	
	
	@Select("SELECT * FROM game_count_num_order where 1=1 and count_id=#{countNumId} and user_id=#{userId} limit 1")
	CountNumOrder findByCountNumIdAndUserIdOne(@Param("countNumId") long countNumId, @Param("userId") long userId);

	@Select("SELECT count(id) FROM game_count_num_order where 1=1 and count_id=#{countNumId} and user_id=#{userId}")
	long countByCountIdAndUserId(@Param("countNumId") long countNumId, @Param("userId") long userId);

	@Select("SELECT count(id) FROM game_count_num_order where 1=1 and count_id=#{countNumId}")
	long countByCountId(@Param("countNumId") long countNumId);

	@Select("select * from "
			+ "(SELECT a.count_id as countNumId,a.id as countNumOrderId,a.count_title as title,a.order_no as orderNo,a.user_id as userId,a.user_name as userName,a.num as num,a.create_time as createTime,a.valid_time as validTime,a.join_num as joinNum,(@curRank := @curRank + 1) AS rank "
			+ " FROM game_count_num_order a, (SELECT @curRank := 0) b  where a.count_id=#{countNumId} ORDER BY a.num desc,valid_time asc ) aa where  aa.userId=#{userId} ORDER BY aa.num desc,aa.validTime asc limit 1")
	CountNumGameOrderRankDto userRankingByCountNumIdGroupNum(@Param("countNumId") long countNumId,
			@Param("userId") long userId);

	@Select("select * from "
			+ "(SELECT a.count_id as countNumId,a.id as countNumOrderId,a.count_title as title,a.order_no as orderNo,a.user_id as userId,a.user_name as userName,a.num as num,a.create_time as createTime,a.valid_time as validTime,a.join_num as joinNum,(@curRank := @curRank + 1) AS rank "
			+ " FROM game_count_num_order a, (SELECT @curRank := 0) b  where a.count_id=#{countNumId} and a.num>0 ORDER BY a.num asc,valid_time asc ) aa where  aa.userId=#{userId} ORDER BY aa.num asc,aa.validTime asc limit 1")
	CountNumGameOrderRankDto userRankingByCountNumIdGroupTime(@Param("countNumId") long countNumId,
			@Param("userId") long userId);

	// 多记录排名，排序优化
	/***
	 * select b.id,b.order_no,a.user_id,a.maxNum,b.create_time,b.count_id from
	 * (select user_id,count_id,max(num) as maxNum from game_count_num_order where
	 * 1=1 and count_id=6 GROUP BY user_id ORDER BY maxNum desc LIMIT 50) a LEFT
	 * JOIN game_count_num_order b on a.user_id=b.user_id and a.maxNum=b.num and
	 * a.count_id=b.count_id GROUP BY a.user_id ORDER BY a.maxNum desc,
	 * b.create_time asc,b.id;
	 * 
	 */

}
