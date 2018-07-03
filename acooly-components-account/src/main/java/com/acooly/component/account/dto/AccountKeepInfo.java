package com.acooly.component.account.dto;

import com.acooly.component.account.service.tradecode.TradeCode;
import com.acooly.core.utils.Money;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 记账信息
 *
 * @author zhangpu@acooly.cn
 * @date 2018-06-19
 */
@Getter
@Setter
public class AccountKeepInfo extends AccountInfo {

    /**
     * 记账类型
     */
    @NotNull
    private TradeCode tradeCode;

    /**
     * 记账金额
     */
    @NotNull
    private Money amount;

    private Long busiId;

    @Size(max = 1024)
    private String busiData;


    public AccountKeepInfo() {
    }

    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount, Long busiId, String busiData) {
        super(accountId, null, null, null);
        this.tradeCode = tradeCode;
        this.amount = amount;
        this.busiId = busiId;
        this.busiData = busiData;
    }

    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount) {
        this(accountId, tradeCode, amount, null, null);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper("AccountKeepInfo")
                .add("accountId", getAccountId())
                .add("accountNo", getAccountNo())
                .add("userId", getUserId())
                .add("username", getUsername())
                .add("tradeCode", tradeCode.code() + "/" + tradeCode.message())
                .add("direction", tradeCode.direction())
                .add("amount", amount)
                .add("accountType", getAccountType())
                .omitNullValues()
                .toString();
    }
}
