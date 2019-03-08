package com.acooly.module.account.service.impl;

import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.service.AccountManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 账户管理服务实现
 *
 * @author zhangpu@acooly.cn
 * @date 2018-06-23 15:03
 */
@Component
@Slf4j
public class AccountManageServiceImpl extends AccountSupportService implements AccountManageService {

    @Override
    public Account openAccount(AccountInfo accountInfo) {
        doCheck(accountInfo);
        return accountService.createAccount(accountInfo);
    }

    @Override
    public Account loadAccount(AccountInfo accountInfo) {
        return accountService.loadAccount(accountInfo);
    }

    @Override
    public void statusChange(Long accountId, SimpleStatus status) {
        accountService.statusChange(accountId, status);
    }

    @Override
    public void statusChange(String accountNo, SimpleStatus status) {
        accountService.statusChange(accountNo, status);
    }
}
