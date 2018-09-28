package com.acooly.module.account.service;

import com.acooly.core.utils.Money;
import com.acooly.module.account.dto.AccountKeepInfo;
import com.acooly.module.account.dto.TransferInfo;
import com.acooly.module.account.TradeCode;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 账务交易服务
 *
 * @author zhangpu
 */
public interface AccountTradeService {


    /**
     * 单笔记账
     * 用于单边交易记账，如：充值，提现，冻结等
     *
     * @param accountTradeInfo
     */
    void keepAccount(AccountKeepInfo accountTradeInfo);

    /**
     * 批量记账
     * <p>
     * 用户多变交易记账，如：付款等
     * 逻辑为：按传入list列表的顺序执行
     *
     * @param accountTradeInfos
     * @return 批次号
     */
    String keepAccounts(List<AccountKeepInfo> accountTradeInfos);

    /**
     * 批量记账
     * <p>
     * 用户多变交易记账，如：付款等
     * 逻辑为：按传入list列表的顺序执行
     *
     * @param accountTradeInfos
     * @param comments
     * @return 批次号
     */
    String keepAccounts(List<AccountKeepInfo> accountTradeInfos, @Nullable String comments);


    /**
     * 冻结
     *
     * @param accountId 账户ID
     * @param amount    冻结金额
     * @param comments  可为空
     */
    void freeze(Long accountId, Money amount, @Nullable String comments);

    /**
     * 批量冻结
     *
     * @param accountIds
     * @param comments   可为空
     * @return batchNo
     */
    String freeze(List<Long> accountIds, Money amount, @Nullable String comments);

    /**
     * 解冻
     *
     * @param accountId
     * @param amount    冻结金额
     * @param comments  可为空
     */
    void unfreeze(Long accountId, Money amount, @Nullable String comments);

    /**
     * 批量解冻
     *
     * @param accountIds
     * @param amount     冻结金额
     * @param comments   可为空
     * @return batchNo
     */
    String unfreeze(List<Long> accountIds, Money amount, @Nullable String comments);


    /**
     * 充值记账 包装接口
     *
     * @param accountId 账户ID
     * @param amount    金额
     * @param tradeCode 充值特殊交易码
     * @param comments  备注
     */
    void deposit(Long accountId, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments);


    /**
     * 充值记账
     *
     * @param accountId 账户ID
     * @param amount    金额
     */
    void deposit(Long accountId, Money amount);

    /**
     * 充值记账
     *
     * @param userNo 用户名
     * @param accountType 账户类型
     * @param bizOrderNo 业务订单号
     * @param amount    金额
     */
    void deposit(String userNo,String accountType,String bizOrderNo,Money amount,String comments);


    /**
     * 提现包装
     *
     * @param accountId
     * @param amount
     * @param tradeCode
     * @param comments
     */
    void withdraw(Long accountId, Money amount, @Nullable TradeCode tradeCode, @Nullable String comments);

    /**
     * 提现包装
     *
     * @param accountId
     * @param amount
     */
    void withdraw(Long accountId, Money amount);

    /**
     * 单笔转账
     *
     * @param transferInfo
     * @return batchNo
     */
    String transfer(TransferInfo transferInfo);

    /**
     * 批量自由转账
     * <p>
     * 默认最大600
     *
     * @param transferInfos
     * @return batchNo
     */
    String transfer(List<TransferInfo> transferInfos);


}
