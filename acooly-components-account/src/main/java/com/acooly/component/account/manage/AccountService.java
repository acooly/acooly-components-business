/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 *
 */
package com.acooly.component.account.manage;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.core.common.service.EntityService;

/**
 * 账户信息 Service接口
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
public interface AccountService extends EntityService<Account> {


    /**
     * 加载账户
     * <p>
     * 三种方式加载：accountId or accountNo or userId+accountType
     *
     * @return
     */
    Account loadAccount(AccountInfo accountInfo);

    Account createAccount(AccountInfo accountInfo);


    Account loadAndCreate(AccountInfo accountInfo);


    Account loadAndLock(Long id);


}
