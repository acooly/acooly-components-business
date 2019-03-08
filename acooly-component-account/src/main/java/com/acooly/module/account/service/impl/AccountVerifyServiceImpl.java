package com.acooly.module.account.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.entity.AccountBill;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.exception.AccountErrorEnum;
import com.acooly.module.account.exception.AccountOperationException;
import com.acooly.module.account.exception.AccountVerifyException;
import com.acooly.module.account.manage.AccountBillService;
import com.acooly.module.account.manage.AccountService;
import com.acooly.module.account.service.AccountVerifyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-10 02:31
 */
@Slf4j
@Component
public class AccountVerifyServiceImpl implements AccountVerifyService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBillService accountBillService;

    @Override
    public void verifyAccount(AccountInfo accountInfo) {
        Account account = accountService.loadAccount(accountInfo);
        int pageSize = 10;
        int pageNo = 1;
        PageInfo<AccountBill> pageInfo = new PageInfo<>(pageSize, pageNo);
        Map<String, Object> map = new HashMap<>();
        map.put("EQ_accountId", account.getId());
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("id", true);
        pageInfo = accountBillService.query(pageInfo, map, sortMap);
        LinkedList<AccountBill> accountBills = Lists.newLinkedList(pageInfo.getPageResults());

        long prevBalancePost = doVerifyAccountBillOnePage(accountBills, null);
        if (pageInfo.getTotalPage() > 1) {
            for (int i = 2; i <= pageInfo.getTotalPage(); i++) {
                pageInfo = new PageInfo<>(pageSize, i);
                pageInfo = accountBillService.query(pageInfo, map, sortMap);
                accountBills = Lists.newLinkedList(pageInfo.getPageResults());
                prevBalancePost = doVerifyAccountBillOnePage(accountBills, prevBalancePost);
            }
        }

        Long latestPostBalance = prevBalancePost;
        if (!latestPostBalance.equals(account.getBalance())) {
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_VERIFY_LATEST_BALANCE_FAIL,
                    "最新交易后余额:" + latestPostBalance + ", 账户余额:" + account.getBalance());
        }
        log.info("账户验证 {} - 账户验证 - 通过。账户流水:{},最新交易后余额:{},账户余额:{}", account.getLabel(), pageInfo.getTotalCount(), latestPostBalance, account.getBalance());
    }

    private long doVerifyAccountBillOnePage(LinkedList<AccountBill> accountBills, Long firstPrevBalancePost) {
        Long prevBalancePost = firstPrevBalancePost;
        if (prevBalancePost == null) {
            prevBalancePost = accountBills.getFirst().getBalancePost();
        }
        AccountBill accountBill = null;
        int start = (firstPrevBalancePost == null ? 1 : 0);
        for (int i = start; i < accountBills.size(); i++) {
            accountBill = accountBills.get(i);
            long amount = accountBill.getAmount();

            long calcBalancePost = prevBalancePost;
            if (accountBill.getDirection() == DirectionEnum.in) {
                calcBalancePost = prevBalancePost + amount;
            } else if (accountBill.getDirection() == DirectionEnum.out) {
                calcBalancePost = prevBalancePost - amount;
            } else {
                // 冻结
                continue;
            }

            if (accountBill.getBalancePost() != calcBalancePost) {
                log.warn("账户流水验证 [未通过] accountBill:{}", accountBill);
                throw new AccountVerifyException(AccountErrorEnum.ACCOUNT_VERIFY_BILL_FAIL);
            }
            prevBalancePost = accountBill.getBalancePost();
        }
        return prevBalancePost;
    }

}
