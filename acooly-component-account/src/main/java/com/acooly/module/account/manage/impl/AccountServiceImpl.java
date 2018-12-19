/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.manage.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.account.dao.AccountDao;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.exception.AccountErrorEnum;
import com.acooly.module.account.exception.AccountOperationException;
import com.acooly.module.account.manage.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户信息 Service实现
 * <p>
 * Date: 2018-06-06 10:40:46
 *
 * @author acooly
 */
@Slf4j
@Service("accountService")
public class AccountServiceImpl extends EntityServiceImpl<Account, AccountDao> implements AccountService {


    @Override
    public Account loadAccount(AccountInfo accountInfo) {

        Account account = null;
        // 1、优先ID加载
        if (accountInfo.getAccountId() != null) {
            account = getEntityDao().findById(accountInfo.getAccountId());
            if (account != null) {
                return account;
            }
        }
        // 2、accountNo加载
        if (Strings.isNoneBlank(accountInfo.getAccountNo())) {
            account = getEntityDao().findByAccountNo(accountInfo.getAccountNo());
            if (account != null) {
                return account;
            }
        }
        // 3、用户ID+账户类型加载
        if (accountInfo.getUserId() != null && accountInfo.getAccountType() != null) {
            account = getEntityDao().findByUserIdAndAccountType(accountInfo.getUserId(), accountInfo.getAccountType());
            if (account != null) {
                return account;
            }
        }
        // 4、用户编码+账户类型加载
        if (Strings.isNotBlank(accountInfo.getUserNo()) && accountInfo.getAccountType() != null) {
            account = getEntityDao().findByUserNoAndAccountType(accountInfo.getUserNo(), accountInfo.getAccountType());
            if (account != null) {
                return account;
            }
        }
        return null;
    }


    @Override
    public Account createAccount(AccountInfo accountInfo) {
        Account account = loadAccount(accountInfo);
        if (account != null) {
            log.info("开户 失败 {},accountInfo:{}", AccountErrorEnum.ACCOUNT_ALREADT_EXISTED, accountInfo);
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_ALREADT_EXISTED);
        }

        account = BeanCopier.copy(accountInfo, Account.class);
        account.setAccountType(accountInfo.getAccountType());

        if (Strings.isNotBlank(account.getUserNo())) {
            //
            if (Strings.isBlank(account.getAccountNo())) {
                //AccountNo为空 生产一个，不关联UserNo，多账户模式
                account.setAccountNo(Ids.getDid());
            }
        } else {
            // UserNo和AccountNo都为空，则生成并设置为一致
            if (Strings.isBlank(account.getAccountNo())) {
                account.setUserNo(Ids.getDid());
                account.setAccountNo(account.getUserNo());
            } else {
                account.setUserNo(account.getAccountNo());
            }
        }
        save(account);
        return account;
    }

    @Override
    public Account loadAndLock(Long id) {
        return getEntityDao().findAndLockById(id);
    }


    @Override
    public void statusChange(Long id, SimpleStatus status) {
        doStatusChange(new AccountInfo(id), status);
    }

    @Override
    public void statusChange(String accountNo, SimpleStatus status) {
        doStatusChange(new AccountInfo(accountNo), status);
    }

    @Override
    public List<String> getAllaccountType() {
        return getEntityDao().getAllaccountType();
    }


    protected void doStatusChange(AccountInfo accountInfo, SimpleStatus status) {
        try {
            Account account = loadAccount(accountInfo);
            if (account == null) {
                log.warn("状态变更 [失败] account key:{},原因：{}", accountInfo.getLabel(), AccountErrorEnum.ACCOUNT_NOT_EXIST);
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_NOT_EXIST, "account key:" + accountInfo.getLabel());
            }

            if (account.getStatus() == SimpleStatus.disable) {
                log.warn("状态变更 [失败] 账户状态为禁用，不能直接修改状态。account{}, status:{}", account.getLabel(), SimpleStatus.disable);
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_STATUS_NOT_ALLOW_CHANGE, "account:" + account.getLabel());
            }

            if (account.getStatus() == status) {
                log.info("状态变更 [成功] 指定修改的状态与实体本来状态一致，直接返回. status:{}", status);
                return;
            }
            SimpleStatus orginalStatus = account.getStatus();
            account.setStatus(status);
            update(account);
            log.info("状态变更 [成功] account:{} , status: {} -> {}", account.getLabel(), orginalStatus, status);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException(AccountErrorEnum.ACCOUNT_INTERNAL_ERROR);
        }
    }

}
