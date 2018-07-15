package com.acooly.component.account.service;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.dto.TransferInfo;
import com.acooly.component.account.entity.Account;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.enums.SimpleStatus;

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
     * @return
     */
    String keepAccounts(List<AccountKeepInfo> accountTradeInfos, @Nullable String comments);


    /**
     * 冻结
     *
     * @param accountId
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
     * @param comments  可为空
     */
    void unfreeze(Long accountId, Money amount, @Nullable String comments);

    /**
     * 批量解冻
     *
     * @param accountIds
     * @param comments   可为空
     * @return batchNo
     */
    String unfreeze(List<Long> accountIds, Money amount, @Nullable String comments);


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
