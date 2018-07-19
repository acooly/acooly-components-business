/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.manage.impl;

import com.acooly.module.account.dao.AccountTradeCodeDao;
import com.acooly.module.account.entity.AccountTradeCode;
import com.acooly.module.account.manage.AccountTradeCodeService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 交易编码定义 Service实现
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
@Service("accountTradeCodeService")
public class AccountTradeCodeServiceImpl extends EntityServiceImpl<AccountTradeCode, AccountTradeCodeDao> implements AccountTradeCodeService {

    @Override
    public AccountTradeCode getTradeCode(String tradeCode) {
        return getEntityDao().findByTradeCode(tradeCode);
    }


    //    @Cacheable(value = "cacheName",key = "allTradeCodes")
    @Override
    public List<AccountTradeCode> getAll() throws BusinessException {
        return super.getAll();
    }


    //    @CacheEvict(value = "cachaName" ,key = "allTradeCodes")
    @Override
    public void removeById(Serializable id) throws BusinessException {
        super.removeById(id);
    }

    //    @CacheEvict(value = "cachaName" ,key = "allTradeCodes")
    @Override
    public void save(AccountTradeCode o) throws BusinessException {
        super.save(o);
    }

    //    @CacheEvict(value = "cachaName" ,key = "allTradeCodes")
    @Override
    public void update(AccountTradeCode o) throws BusinessException {
        super.update(o);
    }
}
