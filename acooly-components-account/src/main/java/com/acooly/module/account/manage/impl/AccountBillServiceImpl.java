/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.manage.impl;

import com.acooly.module.account.dao.AccountBillDao;
import com.acooly.module.account.entity.AccountBill;
import com.acooly.module.account.manage.AccountBillService;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;

/**
 * 账户进出账 Service实现
 *
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 *
 */
@Service("accountBillService")
public class AccountBillServiceImpl extends EntityServiceImpl<AccountBill, AccountBillDao> implements AccountBillService {

}
