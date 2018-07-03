/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.manage.impl;

import com.acooly.component.account.dao.AccountDao;
import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.manage.AccountService;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanCopier;
import org.springframework.stereotype.Service;

/**
 * 账户信息 Service实现
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
@Service("accountService")
public class AccountServiceImpl extends EntityServiceImpl<Account, AccountDao> implements AccountService {

    private static final String ACCOUNT_NO_PRFIX = "AC";

    @Override
    public Account loadAccount(AccountInfo accountInfo) {

        if (accountInfo.getAccountId() != null) {
            return getEntityDao().findById(accountInfo.getAccountId());
        }

        if (Strings.isNoneBlank(accountInfo.getAccountNo())) {
            return getEntityDao().findByAccountNo(accountInfo.getAccountNo());
        }

        if (accountInfo.getUserId() != null && accountInfo.getAccountType() != null) {
            return getEntityDao().findByUserIdAndAccountType(accountInfo.getUserId(), accountInfo.getAccountType());
        }

        return null;
    }

    @Override
    public Account createAccount(AccountInfo accountInfo) {
        Account account = loadAccount(accountInfo);
        if (account != null) {
            return account;
        }
        account = BeanCopier.copy(accountInfo, Account.class);
        account.setId(accountInfo.getAccountId());
        account.setComments(null);
        if (Strings.isBlank(account.getAccountNo())) {
            account.setAccountNo(Ids.getDid(ACCOUNT_NO_PRFIX));
        }
        save(account);
        return account;
    }

    @Override
    public Account loadAndCreate(AccountInfo accountInfo) {
        Account account = loadAccount(accountInfo);
        if (account == null) {
            account = createAccount(accountInfo);
        }
        return account;
    }

    @Override
    public Account loadAndLock(Long id) {
        return getEntityDao().findAndLockById(id);
    }
}
