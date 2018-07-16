package com.acooly.component.account.service.impl;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.exception.AccountErrorEnum;
import com.acooly.component.account.exception.AccountOperationException;
import com.acooly.component.account.manage.AccountService;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.core.utils.validate.Validators;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-16 01:17
 */
@Slf4j
public class AccountSupportService {

    @Autowired
    protected AccountService accountService;

    /**
     * 参数合法性检查
     *
     * @param accountInfo
     */
    protected void doCheck(AccountInfo accountInfo) {
        Validators.assertJSR303(accountInfo);
        accountInfo.check();
    }

    protected void doCheckAccount(Account account, AccountKeepInfo accountKeepInfo) {
        if (account == null) {
            log.error("记账 [失败] AccountKeepInfo: {}, 错误:{}", accountKeepInfo,
                    AccountErrorEnum.ACCOUNT_NOT_EXIST.getLabel());
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_NOT_EXIST);
        }

        if (account.getStatus() != SimpleStatus.enable) {
            log.error("记账 [失败] AccountKeepInfo: {}, 错误:{} - status:{}", accountKeepInfo,
                    AccountErrorEnum.ACCOUNT_STATUS_ERROR.getLabel(), account.getStatus());
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_STATUS_ERROR);
        }

    }

    protected Long loadAccountId(AccountKeepInfo accountKeepInfo) {
        // 获取主键ID
        Long id = accountKeepInfo.getAccountId();
        Account account = null;
        if (id == null) {
            account = accountService.loadAccount(accountKeepInfo);
            if (account == null) {
                log.error("记账 [失败] AccountKeepInfo: {}, 错误:{}", accountKeepInfo, AccountErrorEnum.ACCOUNT_NOT_EXIST.getLabel());
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_NOT_EXIST);
            }
            id = account.getId();
        }
        return id;
    }

}