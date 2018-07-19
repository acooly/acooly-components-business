/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.dao;

import com.acooly.module.account.entity.AccountTradeCode;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 交易编码定义 Mybatis Dao
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
public interface AccountTradeCodeDao extends EntityMybatisDao<AccountTradeCode> {


    /**
     * 交易编码查询交易码配置信息
     *
     * @param tradeCode
     * @return
     */
    @Select("select * from ac_account_trade_code where trade_code = #{tradeCode}")
    AccountTradeCode findByTradeCode(@Param("tradeCode") String tradeCode);


}
