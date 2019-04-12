/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by cuifuq
 * date:2019-03-05
 */
package com.acooly.module.redpack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.redpack.entity.RedPackOrder;

/**
 * 红包订单 Mybatis Dao
 *
 * Date: 2019-03-05 18:18:09
 * 
 * @author cuifuq
 */
public interface RedPackOrderDao extends EntityMybatisDao<RedPackOrder> {

	@Select("select * from red_red_pack_order where id = #{id} for update")
	RedPackOrder lockById(long id);

	@Select("select * from red_red_pack_order where red_pack_id = #{redPackId}")
	List<RedPackOrder> findByRedPackId(@Param("redPackId") Long redPackId);

	@Select("select * from red_red_pack_order where red_pack_id = #{redPackId} and user_id=#{userId} ")
	List<RedPackOrder> findByRedPackIdAndUserId(@Param("redPackId") Long redPackId, @Param("userId") Long userId);

	@Select("select id from red_red_pack_order where amount=(select max(amount) from red_red_pack_order where type='RED_PACK' and red_pack_id = #{redPackId}) LIMIT 0,1")
	Long findByRedPackMaxId(@Param("redPackId") Long redPackId);

	@Select("select id from red_red_pack_order where amount=(select max(amount) from red_red_pack_order where red_pack_id = #{redPackId}) LIMIT 0,1")
	RedPackOrder findByRedPackOrderMaxId(@Param("redPackId") Long redPackId);

	@Update("update red_red_pack_order set is_first='YES' where id=#{id} ")
	void updateIsFirst(@Param("id") Long id);

	@Select("select count(id) from red_red_pack_order WHERE red_pack_id = #{redPackId} and status=#{status}  and type=#{type}")
	long countRedPackByRedPackIdAndStatusAndType(@Param("redPackId") Long redPackId, @Param("status") String status,
			@Param("type") String type);

	@Select("select IFNULL(sum(amount),0) as sumAmount from red_red_pack_order WHERE red_pack_id = #{redPackId} and status=#{status}")
	long sumRedPackByRedPackIdAndStatus(@Param("redPackId") Long redPackId, @Param("status") String status);

	@Select("select count(id) from red_red_pack_order WHERE red_pack_id = #{redPackId} and user_id=#{userId}")
	Long coutRedOrderNum(@Param("userId") long userId, @Param("redPackId") long redPackId);

}
