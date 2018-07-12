package com.acooly.component.account.service.impl;

import com.acooly.component.account.AccountProperties;
import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.dto.TransferInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.component.account.entity.AccountBill;
import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.component.account.exception.AccountErrorEnum;
import com.acooly.component.account.exception.AccountOperationException;
import com.acooly.component.account.manage.AccountBillService;
import com.acooly.component.account.manage.AccountService;
import com.acooly.component.account.service.AccountTradeService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Strings;
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
    @Autowired
    private AccountProperties accountProperties;

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public void keepAccount(AccountKeepInfo accountKeepInfo) {
        Account account = null;
        AccountBill accountBill = null;
        try {
            doCheck(accountKeepInfo);
            Long id = loadAccountId(accountKeepInfo);
            // 悲观锁加载
            account = accountService.loadAndLock(id);
            if (account == null) {
                log.error("记账 [失败] AccountKeepInfo: {}, 错误:{}", accountKeepInfo, AccountErrorEnum.ACCOUNT_NOT_EXIST.getLabel());
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_NOT_EXIST);
            }
            doAccountModify(account, accountKeepInfo);
            accountBill = doAccountBill(account, accountKeepInfo);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("记账 [失败] AccountKeepInfo: {}, 错误:{}", accountKeepInfo, e.getMessage());
            throw new BusinessException(AccountErrorEnum.ACCOUNT_INTERNAL_ERROR);
        }
        log.info("记账 成功：{} - {} - {} - {}", account.getLabel(), accountKeepInfo.getTradeCode().lable(), accountKeepInfo.getAmount().getCent(),
                (accountBill != null ? accountBill.getBalancePost() : "-"));
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public String keepAccounts(List<AccountKeepInfo> accountKeepInfos, String comments) {
        if (Collections3.isEmpty(accountKeepInfos)) {
            log.warn("批量记账 {} - 验证失败:{}", comments, AccountErrorEnum.ACCOUNT_BATCH_LIST_IS_EMPTY.getLabel());
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_BATCH_LIST_IS_EMPTY);
        }
        if (accountKeepInfos.size() > accountProperties.getBatchMaxSize()) {
            log.warn("批量记账 {} - 验证失败:{}", comments, AccountErrorEnum.ACCOUNT_BATCH_NOT_ALLOW_OVER_MAX.getLabel());
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_BATCH_NOT_ALLOW_OVER_MAX);
        }
        String batchNo = Collections3.getFirst(accountKeepInfos).getBatchNo();
        for (AccountKeepInfo accountKeepInfo : accountKeepInfos) {
            if (Strings.equals(accountKeepInfo.getBatchNo(), batchNo)) {
                batchNo = accountKeepInfo.getBatchNo();
            } else {
                log.warn("批量记账 验证失败 原因:{}, accountKeepInfo: {}", AccountErrorEnum.ACCOUNT_BATCH_KEEP_DIFFERENT, accountKeepInfo.toString());
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_BATCH_KEEP_DIFFERENT, accountKeepInfo.toString());
            }
        }
        if (Strings.isBlank(batchNo)) {
            batchNo = Ids.getDid();
        }
        log.info("批量记账 启动 batchNo:{}, {}", batchNo, comments);
        for (AccountKeepInfo accountKeepInfo : accountKeepInfos) {
            accountKeepInfo.setBatchNo(batchNo);
            keepAccount(accountKeepInfo);
        }
        log.info("批量记账 成功 batchNo:{} ,size:{}, {}", batchNo, accountKeepInfos.size(), comments);
        return batchNo;
    }


    @Override
    public Account openAccount(AccountInfo accountInfo) {
        doCheck(accountInfo);
        return accountService.createAccount(accountInfo);
    }

    @Transactional(rollbackOn = Throwable.class)
    @Override
    public String keepAccounts(List<AccountKeepInfo> accountKeepInfos) {
        return keepAccounts(accountKeepInfos, "");
    }


    @Override
    public String transfer(TransferInfo transferInfo) {
        return keepAccounts(convertTransferToAccountKeepInfos(transferInfo), "单笔转账");
    }

    @Override
    public String transfer(List<TransferInfo> transferInfos) {
        List<AccountKeepInfo> accountKeepInfos = Lists.newArrayList();
        for (TransferInfo transferInfo : transferInfos) {
            accountKeepInfos.addAll(convertTransferToAccountKeepInfos(transferInfo));
        }
        return keepAccounts(accountKeepInfos, "批量转账");
    }

    protected List<AccountKeepInfo> convertTransferToAccountKeepInfos(TransferInfo transferInfo) {
        AccountKeepInfo from = new AccountKeepInfo(transferInfo.getFrom(),
                transferInfo.getTradeCodeFrom(), transferInfo.getAmount(), transferInfo.getComments());
        from.setBatchNo(transferInfo.getBatchNo());
        AccountKeepInfo to = new AccountKeepInfo(transferInfo.getTo(),
                transferInfo.getTradeCodeTo(), transferInfo.getAmount(), transferInfo.getComments());
        to.setBatchNo(transferInfo.getBatchNo());
        return Lists.newArrayList(from, to);
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
            } else if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.in) {
                account.setBalance(account.getBalance() + amount.getCent());
            } else {
                account.setBalance(account.getFreeze() + amount.getCent());
            }
        }
        accountService.update(account);
        log.debug("记账 [余额变动] 成功。{} - {}/{}/{} - {}", account.getLabel(), accountKeepInfo.getTradeCode().code(),
                accountKeepInfo.getTradeCode().message(), accountKeepInfo.getTradeCode().direction(),
                accountKeepInfo.getAmount().getCent());
    }

    protected AccountBill doAccountBill(Account account, AccountKeepInfo accountKeepInfo) {
        AccountBill accountBill = new AccountBill();
        accountBill.setBillNo(Ids.oid());
        accountBill.setAccountId(account.getId());
        accountBill.setAccountNo(account.getAccountNo());
        accountBill.setUserId(account.getUserId());
        accountBill.setUserNo(account.getUserNo());

        accountBill.setAmount(accountKeepInfo.getAmount().getCent());
        accountBill.setBalancePost(account.getBalance());

        accountBill.setDirection(accountKeepInfo.getTradeCode().direction());
        accountBill.setComments(accountKeepInfo.getComments());
        accountBill.setTradeCode(accountKeepInfo.getTradeCode().code());
        accountBill.setUsername(account.getUsername());
        accountBill.setBusiId(accountKeepInfo.getBusiId());
        accountBill.setBusiData(accountKeepInfo.getBusiData());
        accountBill.setBatchNo(accountKeepInfo.getBatchNo());
        accountBillService.save(accountBill);
        log.debug("记账 [流水记账] 成功。{} - {}/{}/{} - {}", account.getLabel(), accountKeepInfo.getTradeCode().code(),
                accountKeepInfo.getTradeCode().message(), accountKeepInfo.getTradeCode().direction(),
                accountKeepInfo.getAmount().getCent());
        return accountBill;
    }


}
