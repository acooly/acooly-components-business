/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.component.account.manage.AccountService;
import com.acooly.component.account.dao.AccountDao;
import com.acooly.component.account.entity.Account;

/**
 * 账户信息 Service实现
 *
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 *
 */
@Service("accountService")
public class AccountServiceImpl extends EntityServiceImpl<Account, AccountDao> implements AccountService {

}
