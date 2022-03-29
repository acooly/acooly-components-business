/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.point.entity.PointAccount;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 积分账户 Mybatis Dao
 *
 * <p>Date: 2017-02-03 22:45:12
 *
 * @author cuifuqiang
 */
public interface PointAccountDao extends EntityMybatisDao<PointAccount> {

    @Select("select * from pt_point_account where user_no=#{userNo}")
    PointAccount findByUserNo(@Param("userNo") String userNo);

    @Select("select * from pt_point_account where user_no=#{userNo} for update")
    PointAccount lockByUserNo(@Param("userNo") String userNo);


    @Select(
            "SELECT count(id) FROM pt_point_account where grade_id=#{gradeId} and balance>=(SELECT balance FROM pt_point_account where user_no=#{userNo})")
    int pointRankByUserNoAndGradeId(
            @Param("userNo") String userNo, @Param("gradeId") Long gradeId);

    @Select(
            "SELECT count(id) FROM pt_point_account where balance>=(SELECT balance FROM pt_point_account where user_no=#{userNo})")
    int pointRankByUserNo(@Param("userNo") String userNo);
}
