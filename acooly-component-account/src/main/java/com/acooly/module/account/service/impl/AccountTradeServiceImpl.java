package com.acooly.module.account.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Strings;
import com.acooly.module.account.TradeCode;
import com.acooly.module.account.dto.AccountInfo;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.entity.Account;
import com.acooly.module.account.entity.AccountBill;
import com.acooly.module.account.enums.CommonTradeCodeEnum;
import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.module.account.exception.AccountErrorEnum;
import com.acooly.module.account.exception.AccountOperationException;
import com.acooly.module.account.manage.AccountBillService;
import com.acooly.module.account.service.AccountTradeService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-23 15:03
 */
@Component
@Slf4j
public class AccountTradeServiceImpl extends AccountSupportService implements AccountTradeService {

    @Autowired
    private AccountBillService accountBillService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void keepAccount(AccountKeepInfo accountKeepInfo) {
        doCheck(accountKeepInfo);
        Account account = null;
        AccountBill accountBill = null;
        try {
            Long id = loadAccountId(accountKeepInfo);
            account = accountService.loadAndLock(id);
            doCheckAccount(account, accountKeepInfo);
            doAccountModify(account, accountKeepInfo);
            accountBill = doAccountBill(account, accountKeepInfo);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("记账 [失败] AccountKeepInfo: {}, 错误:{}", accountKeepInfo, e.getMessage());
            throw new BusinessException(AccountErrorEnum.ACCOUNT_INTERNAL_ERROR);
        }
        log.info("记账 成功：{}  {}  {}{}  {}", account.getLabel(), accountKeepInfo.getTradeCode().lable(),
                accountKeepInfo.getTradeCode().symbol(),
                accountKeepInfo.getAmount().getCent(),
                (accountBill != null ? (accountKeepInfo.getTradeCode().direction() == DirectionEnum.keep ? accountBill.getFreezePost()
                        : accountBill.getBalancePost()) : "-"));
    }

    @Transactional(rollbackFor = Throwable.class)
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
        long checkAmountSum = 0;
        for (AccountKeepInfo accountKeepInfo : accountKeepInfos) {
            if (Strings.equals(accountKeepInfo.getBatchNo(), batchNo)) {
                batchNo = accountKeepInfo.getBatchNo();
            } else {
                log.warn("批量记账 验证失败 原因:{}, accountKeepInfo: {}", AccountErrorEnum.ACCOUNT_BATCH_KEEP_DIFFERENT, accountKeepInfo.toString());
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_BATCH_KEEP_DIFFERENT, accountKeepInfo.toString());
            }
            checkAmountSum = checkAmountSum + accountKeepInfo.getSymbolAmount();
        }

        if (checkAmountSum != 0) {
            log.warn("批量记账 {} - 验证失败:{}", comments, AccountErrorEnum.ACCOUNT_BATCH_AMOUNT_SUM_MISMATCH.getLabel());
            throw new AccountOperationException(AccountErrorEnum.ACCOUNT_BATCH_AMOUNT_SUM_MISMATCH);
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

    @Transactional(rollbackFor = Throwable.class)
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


    @Override
    public void freeze(Long accountId, Money amount, @Nullable String comments) {
        doFreeze(AccountInfo.withId(accountId), amount, CommonTradeCodeEnum.freeze, comments);
    }

    @Override
    public void freeze(AccountInfo accountInfo, Money amount, @Nullable String comments) {
        doFreeze(accountInfo, amount, CommonTradeCodeEnum.freeze, comments);
    }

    @Override
    public String freeze(List<Long> accountIds, Money amount, @Nullable String comments) {
        List<AccountInfo> accountInfos = Lists.newArrayList();
        for (Long id : accountIds) {
            accountInfos.add(AccountInfo.withId(id));
        }
        return doFreeze(accountInfos, amount, CommonTradeCodeEnum.freeze, comments);
    }

    @Override
    public void unfreeze(Long accountId, Money amount, @Nullable String comments) {
        doFreeze(AccountInfo.withId(accountId), amount, CommonTradeCodeEnum.unfreeze, comments);
    }

    @Override
    public String unfreeze(List<Long> accountIds, Money amount, @Nullable String comments) {
        List<AccountInfo> accountInfos = Lists.newArrayList();
        for (Long id : accountIds) {
            accountInfos.add(AccountInfo.withId(id));
        }
        return doFreeze(accountInfos, amount, CommonTradeCodeEnum.unfreeze, comments);
    }

    @Override
    public void unfreeze(AccountInfo accountInfo, Money amount, @Nullable String comments) {
        doFreeze(accountInfo, amount, CommonTradeCodeEnum.unfreeze, comments);
    }

    @Override
    public String freezes(List<AccountInfo> accountInfos, Money amount, @Nullable String comments) {
        return doFreeze(accountInfos, amount, CommonTradeCodeEnum.freeze, comments);
    }


    @Override
    public String unfreezes(List<AccountInfo> accountInfos, Money amount, @Nullable String comments) {
        return doFreeze(accountInfos, amount, CommonTradeCodeEnum.unfreeze, comments);
    }


    /**
     * 充值实现组
     */
    @Override
    public void deposit(Long accountId, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments) {
        doDeposit(AccountInfo.withId(accountId), tradeCode, null, amount, null);
    }

    @Override
    public void deposit(Long accountId, Money amount) {
        deposit(accountId, amount, CommonTradeCodeEnum.deposit, null);
    }

    @Override
    public void deposit(AccountInfo accountInfo, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments) {
        doDeposit(accountInfo, tradeCode, null, amount, null);
    }

    @Override
    public void deposit(AccountInfo accountInfo, Money amount) {
        doDeposit(accountInfo, CommonTradeCodeEnum.deposit, null, amount, null);
    }

    @Override
    public void deposit(String userNo, String accountType, String bizOrderNo, Money amount, String comments) {
        AccountInfo accountInfo = new AccountInfo(userNo, accountType);
        doDeposit(accountInfo, CommonTradeCodeEnum.deposit, bizOrderNo, amount, comments);
    }


    protected void doDeposit(AccountInfo accountInfo, @Nullable TradeCode tradeCode, String bizOrderNo, Money amount, String comments) {
        if (tradeCode == null) {
            tradeCode = CommonTradeCodeEnum.deposit;
        }
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(accountInfo, tradeCode, amount, comments);
        accountKeepInfo.setBizOrderNo(bizOrderNo);
        keepAccount(accountKeepInfo);
    }


    @Override
    public void withdraw(Long accountId, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments) {
        doWithdraw(AccountInfo.withId(accountId), tradeCode, null, amount, comments);
    }

    @Override
    public void withdraw(Long accountId, Money amount) {
        withdraw(accountId, amount, CommonTradeCodeEnum.withdraw, null);
    }


    @Override
    public void withdraw(AccountInfo accountInfo, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments) {
        doWithdraw(accountInfo, tradeCode, null, amount, comments);
    }

    @Override
    public void withdraw(AccountInfo accountInfo, Money amount) {
        doWithdraw(accountInfo, CommonTradeCodeEnum.withdraw, null, amount, null);
    }

    protected void doWithdraw(AccountInfo accountInfo, @Nullable TradeCode tradeCode, String bizOrderNo, Money amount, String comments) {
        if (tradeCode == null) {
            tradeCode = CommonTradeCodeEnum.withdraw;
        }
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(accountInfo, tradeCode, amount, comments);
        accountKeepInfo.setBizOrderNo(bizOrderNo);
        keepAccount(accountKeepInfo);
    }

    /**
     * 单笔冻结/解冻实现
     *
     * @param accountInfo
     * @param amount
     * @param tradeCode
     * @param comments
     */
    protected void doFreeze(AccountInfo accountInfo, Money amount, TradeCode tradeCode, @Nullable String comments) {
        AccountKeepInfo accountKeepInfo = new AccountKeepInfo(accountInfo, tradeCode, amount, comments);
        keepAccount(accountKeepInfo);
    }


    /**
     * 批量冻结/解冻实现
     *
     * @param accountInfos
     * @param amount
     * @param tradeCode
     * @param comments
     * @return
     */
    protected String doFreeze(List<AccountInfo> accountInfos, Money amount, TradeCode tradeCode, @Nullable String comments) {
        List<AccountKeepInfo> accountKeepInfos = Lists.newArrayList();
        for (AccountInfo accountInfo : accountInfos) {
            accountKeepInfos.add(new AccountKeepInfo(accountInfo, tradeCode, amount, comments));
        }
        return keepAccounts(accountKeepInfos, comments);
    }

    protected List<AccountKeepInfo> convertTransferToAccountKeepInfos(TransferInfo transferInfo) {
        AccountKeepInfo from = new AccountKeepInfo(transferInfo.getFrom(),
                transferInfo.getTradeCodeFrom(), transferInfo.getAmount(), transferInfo.getComments());
        from.setBatchNo(transferInfo.getBatchNo());
        if (Strings.isBlank(from.getBizOrderNo())) {
            from.setBizOrderNo(transferInfo.getBizOrderNo());
        }
        if (Strings.isBlank(from.getMerchOrderNo())) {
            from.setMerchOrderNo(transferInfo.getMerchOrderNo());
        }

        AccountKeepInfo to = new AccountKeepInfo(transferInfo.getTo(),
                transferInfo.getTradeCodeTo(), transferInfo.getAmount(), transferInfo.getComments());
        to.setBatchNo(transferInfo.getBatchNo());
        if (Strings.isBlank(to.getBizOrderNo())) {
            to.setBizOrderNo(transferInfo.getBizOrderNo());
        }
        if (Strings.isBlank(to.getMerchOrderNo())) {
            to.setMerchOrderNo(transferInfo.getMerchOrderNo());
        }

        return Lists.newArrayList(from, to);
    }


    protected void doAccountModify(Account account, AccountKeepInfo accountKeepInfo) {
        Money amount = accountKeepInfo.getAmount();
        if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.in) {
            account.setBalance(account.getBalance() + amount.getCent());
        } else if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.out) {
            if (amount.getCent() > account.getAvailable()) {
                log.warn("记账 [{}] 失败, 账户:{}, 可用余额：{}，记账金额: {}, 错误原因：{}, accountKeepInfo:{}", accountKeepInfo.getTradeCode().lable(),
                        account.getLabel(), account.getAvailable(), amount.getCent(), AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE, accountKeepInfo.getLabel());
                throw new AccountOperationException(AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE);
            }
            account.setBalance(account.getBalance() - amount.getCent());
        } else if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.keep) {
            if (accountKeepInfo.getTradeCode().code().equals(CommonTradeCodeEnum.freeze.code())) {
                if (amount.getCent() > account.getAvailable()) {
                    log.warn("记账 [{}] 失败, 账户:{}, 可用余额：{}，记账金额: {}, 错误原因：{},accountKeepInfo:{}", accountKeepInfo.getTradeCode().lable(), account.getLabel(),
                            account.getAvailable(), amount.getCent(), AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE, accountKeepInfo.getLabel());
                    throw new AccountOperationException(AccountErrorEnum.ACCOUNT_INSUFFICIENT_BALANCE);
                }
                account.setFreeze(account.getFreeze() + amount.getCent());
            } else {
                if (amount.getCent() > account.getFreeze()) {
                    log.warn("记账 [{}] 失败, 账户:{}, 总冻结金额：{}，记账金额: {}, 错误原因：{},accountKeepInfo:{}", accountKeepInfo.getTradeCode().lable(), account.getLabel(),
                            account.getFreeze(), amount.getCent(), AccountErrorEnum.ACCOUNT_INSUFFICIENT_FREEZE, accountKeepInfo.getLabel());
                    throw new AccountOperationException(AccountErrorEnum.ACCOUNT_INSUFFICIENT_FREEZE);
                }
                account.setFreeze(account.getFreeze() - amount.getCent());
            }

        }
        accountService.update(account);
        log.info("记账 [动账] 成功。{} - {} - {}", account.getLabel(), accountKeepInfo.getTradeCode().lable(), amount.getCent());
    }

    protected AccountBill doAccountBill(Account account, AccountKeepInfo accountKeepInfo) {
        AccountBill accountBill = new AccountBill();
        accountBill.setBillNo(Ids.oid());
        accountBill.setAccountId(account.getId());
        accountBill.setAccountNo(account.getAccountNo());
        accountBill.setUserId(account.getUserId());
        accountBill.setUserNo(account.getUserNo());
        accountBill.setBizOrderNo(accountKeepInfo.getBizOrderNo());
        accountBill.setMerchOrderNo(accountKeepInfo.getMerchOrderNo());

        if (accountKeepInfo.getTradeCode().direction() == DirectionEnum.keep) {
            accountBill.setFreezeAmount(accountKeepInfo.getAmount().getCent());
            accountBill.setFreezePost(account.getFreeze());
            // 冗余记录当前余额
            accountBill.setBalancePost(account.getBalance());
        } else {
            accountBill.setAmount(accountKeepInfo.getAmount().getCent());
            accountBill.setBalancePost(account.getBalance());
            // 冗余记录当前冻结总额
            accountBill.setFreezePost(account.getFreeze());
        }

        accountBill.setDirection(accountKeepInfo.getTradeCode().direction());
        accountBill.setComments(accountKeepInfo.getComments());
        accountBill.setTradeCode(accountKeepInfo.getTradeCode().code());
        accountBill.setUsername(account.getUsername());
        accountBill.setBusiId(accountKeepInfo.getBusiId());
        accountBill.setBusiData(accountKeepInfo.getBusiData());
        accountBill.setBatchNo(accountKeepInfo.getBatchNo());
        accountBill.setAccountType(account.getAccountType());
        accountBillService.save(accountBill);
        log.info("记账 [流水] 成功。{} - {}/{}/{} - {}", account.getLabel(), accountKeepInfo.getTradeCode().code(),
                accountKeepInfo.getTradeCode().message(), accountKeepInfo.getTradeCode().direction(),
                accountKeepInfo.getAmount().getCent());
        return accountBill;
    }


}
