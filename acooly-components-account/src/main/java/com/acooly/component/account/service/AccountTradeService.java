package com.acooly.component.account.service;

import com.acooly.component.account.dto.KeepAccountInfo;
import com.acooly.component.account.entity.Account;

import java.util.List;

/**
 * 账务交易服务
 *
 * @author zhangpu
 */
public interface AccountTradeService {


    Account createAccount();




    void keepAccount(KeepAccountInfo accountTradeInfo);

    void keepAccounts(List<KeepAccountInfo> accountTradeInfos);

}
