/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.dao;

import com.acooly.module.account.entity.Account;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 账户信息 Mybatis Dao
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
public interface AccountDao extends EntityMybatisDao<Account> {


    @Select("select * from ac_account where user_id = #{userId} and account_type = #{accountType}")
    Account findByUserIdAndAccountType(@Param("userId") Long userId, @Param("accountType") String accountType);

    @Select("select * from ac_account where user_no = #{userNo} and account_type = #{accountType}")
    Account findByUserNoAndAccountType(@Param("userNo") String userNo, @Param("accountType") String accountType);

    @Select("select * from ac_account where account_no = #{accountNo}")
    Account findByAccountNo(@Param("accountNo") String accountNo);

    @Select("select * from ac_account where id = #{id}")
    Account findById(@Param("id") Long id);

    @Select("select * from ac_account where id = #{id} for update")
    Account findAndLockById(@Param("id") Long id);

}
