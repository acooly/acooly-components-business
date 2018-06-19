/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.component.account.manage.AccountBillService;
import com.acooly.component.account.dao.AccountBillDao;
import com.acooly.component.account.entity.AccountBill;

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
