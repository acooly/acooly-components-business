/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-16
 */
package com.acooly.module.member.dao;

import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * b_member_auth Mybatis Dao
 * <p>
 * Date: 2018-10-16 15:57:37
 *
 * @author acooly
 */
public interface MemberAuthDao extends EntityMybatisDao<MemberAuth> {

    /**
     * 根据userId和authRole查询操作员列表
     *
     * @param userId
     * @param authRole
     * @return
     */
    @Select("select * from b_member_auth where user_id = #{userId} and auth_role = #{authRole}")
    List<MemberAuth> findByUserIdAndAuthRole(@Param("userId") Long userId, @Param("authRole") String authRole);

    /**
     * 根据username和authRole查询操作员列表
     *
     * @param username
     * @param authRole
     * @return
     */
    @Select("select * from b_member_auth where username = #{username} and auth_role = #{authRole}")
    List<MemberAuth> findByUsernameAndAuthRole(@Param("username") String username, @Param("authRole") String authRole);

    @Select("select * from b_member_auth where user_id = #{userId} and login_id = #{loginId}")
    MemberAuth findByUserIdAndLoginId(@Param("userId") Long userId, @Param("loginId") String loginId);

    @Select("select * from b_member_auth where username = #{username} and login_id = #{loginId}")
    MemberAuth findByUsernameAndLoginId(@Param("username") String username, @Param("loginId") String loginId);

}
