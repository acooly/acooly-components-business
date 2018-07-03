package com.acooly.component.account.service;

import com.acooly.component.account.dto.AccountInfo;
import com.acooly.component.account.dto.AccountKeepInfo;
import com.acooly.component.account.entity.Account;

import java.util.List;

/**
 * 账务交易服务
 *
 * @author zhangpu
 */
public interface AccountTradeService {


    /**
     * 独立开户
     *
     * @param accountInfo
     * @return
     */
    Account openAccount(AccountInfo accountInfo);

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
    void keepAccounts(List<AccountKeepInfo> accountTradeInfos);

    /**
     * 常用交易:单笔转账
     *
     * @param from
     * @param to
     */
    void transfer(AccountKeepInfo from, AccountKeepInfo to);

}
