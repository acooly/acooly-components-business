/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.dao;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 会员信息 Mybatis Dao
 * <p>
 * Date: 2018-07-23 00:05:26
 *
 * @author acooly
 */
public interface MemberDao extends EntityMybatisDao<Member> {


    @Select("select * from b_member where user_no = #{userNo}")
    Member findUniqueByUserNo(@Param("userNo") String userNo);

    @Select("select * from b_member where username = #{username}")
    Member findUniqueByUsername(@Param("username") String username);


    PageInfo<Member> queryPage(PageInfo<Member> pageInfo, @Param("map") Map<String, Object> map, @Param("sortMap") Map<String, Boolean> sortMap);

}
