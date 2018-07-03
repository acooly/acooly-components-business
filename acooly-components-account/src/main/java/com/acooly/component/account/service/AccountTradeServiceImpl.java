package com.acooly.component.account.service;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.entity.AccountBill;
import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.component.account.exception.AccountErrorEnum;
import com.acooly.component.account.exception.AccountOperationException;
import com.acooly.component.account.manage.AccountBillService;
import com.acooly.component.account.manage.AccountService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.validate.Validators;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-23 15:03
 */
@Component
@Slf4j
public class AccountTradeServiceImpl implements AccountTradeService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBillService accountBillService;


    @Override
    public Account openAccount(AccountInfo accountInfo) {
        doCheck(accountInfo);
        return accountService.createAccount(accountInfo);
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public void keepAccount(AccountKeepInfo accountKeepInfo) {

        doCheck(accountKeepInfo);
        // 获取主键ID
        Long id = accountKeepInfo.getAccountId();
        if (id == null) {
            id = accountService.loadAccount(accountKeepInfo).getId();
        }
        // 悲观锁加载
        Account account = accountService.loadAndLock(id);
        try {
            // 动账
            doAccountModify(account, accountKeepInfo);
            // 记账
            doAccountBill(account, accountKeepInfo);
        } catch (BusinessException be) {
            log.error("记账 [失败] AccountKeepInfo: {}, 错误消息:{}", accountKeepInfo, be.getMessage());
            throw be;
        } catch (Exception e) {
            log.error("记账 [失败] AccountKeepInfo: {}, 内部错误:{}", accountKeepInfo, e.getMessage());
            throw new BusinessException(AccountErrorEnum.ACCOUNT_INTERNAL_ERROR);
        }
        log.info("记账 成功：{} - {}/{}/{} - {}", account.getLabel(),
                accountKeepInfo.getTradeCode().code(), accountKeepInfo.getTradeCode().message(),
                accountKeepInfo.getTradeCode().direction(), accountKeepInfo.getAmount().getCent());
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public void keepAccounts(List<AccountKeepInfo> accountKeepInfos) {
        log.info("批量记账 启动: size:{}", accountKeepInfos.size());
        for (AccountKeepInfo accountKeepInfo : accountKeepInfos) {
            keepAccount(accountKeepInfo);
        }
        log.info("批量记账 成功: size:{}", accountKeepInfos.size());
    }

    @Override
    public void transfer(AccountKeepInfo from, AccountKeepInfo to) {
        keepAccounts(Lists.newArrayList(from, to));
    }

    /**
     * 参数合法性检查
     *
     * @param accountInfo
     */
    protected void doCheck(AccountInfo accountInfo) {
        Validators.assertJSR303(accountInfo);
        accountInfo.check();
    }


    protected void doAccountModify(Account account, AccountKeepInfo accountKeepInfo) {
        Money amount = accountKeepInfo.getAmount();
        if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.in) {
            account.setBalance(account.getBalance() + amount.getCent());
        } else {
            if (amount.getCent() > account.getAvalible()) {
                log.warn("记账 [余额变动] 失败, 账户:{}, 可用余额：{}，记账金额: {}, 错误原因：{}", account.getLabel(),
                        account.getAvalible(), amount.getCent(), AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE);
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE);
            }

            if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.out) {
                account.setBalance(account.getBalance() - amount.getCent());
            } else {
                account.setBalance(account.getFreeze() + amount.getCent());
            }
        }
        accountService.update(account);
        log.debug("记账 [余额变动] 成功。{} - {}/{}/{} - {}", account.getLabel(), accountKeepInfo.getTradeCode().code(),
                accountKeepInfo.getTradeCode().message(), accountKeepInfo.getTradeCode().direction(),
                accountKeepInfo.getAmount().getCent());
    }

    protected void doAccountBill(Account account, AccountKeepInfo accountKeepInfo) {
        AccountBill accountBill = new AccountBill();
        accountBill.setBillNo(Ids.oid());
        accountBill.setAccountId(account.getId());
        accountBill.setAccountNo(account.getAccountNo());
        accountBill.setUserId(account.getUserId());

        accountBill.setAmount(accountKeepInfo.getAmount().getCent());
        accountBill.setBalancePost(account.getBalance());

        accountBill.setDirection(accountKeepInfo.getTradeCode().direction());
        accountBill.setComments(accountKeepInfo.getComments());
        accountBill.setTradeCode(accountKeepInfo.getTradeCode().code());
        accountBill.setUsername(account.getUsername());
        accountBill.setBusiId(accountKeepInfo.getBusiId());
        accountBill.setBusiData(accountKeepInfo.getBusiData());
        accountBillService.save(accountBill);
        log.debug("记账 [流水记账] 成功。{} - {}/{}/{} - {}", account.getLabel(), accountKeepInfo.getTradeCode().code(),
                accountKeepInfo.getTradeCode().message(), accountKeepInfo.getTradeCode().direction(),
                accountKeepInfo.getAmount().getCent());
    }


}
