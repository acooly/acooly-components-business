package com.acooly.module.account.dto;

import com.acooly.module.account.service.tradecode.TradeCode;
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

    /**
     * 如果是批量记账，可以设置本批次的批次号，用于关联本次记账的所有流水，与业务端建立联系。
     * <p>
     * 如果不设置，则组件自动设置，成功记账后，会回写入本属性；如果设置，则请客户端保障批次唯一性。
     */
    private String batchNo;


    public AccountKeepInfo() {
    }

    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount, Long busiId, String busiData) {
        super(accountId, null, null, null, null);
        this.tradeCode = tradeCode;
        this.amount = amount;
        this.busiId = busiId;
        this.busiData = busiData;
    }

    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount) {
        this(accountId, tradeCode, amount, null, null);
    }

    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount, String comments) {
        this(accountId, tradeCode, amount, null, null);
        setComments(comments);
    }

    public AccountKeepInfo(AccountInfo accountInfo, TradeCode tradeCode, Money amount, String comments) {
        this.setAccountId(accountInfo.getAccountId());
        this.setAccountNo(accountInfo.getAccountNo());
        this.setUserId(accountInfo.getUserId());
        this.setUserNo(accountInfo.getUserNo());
        this.tradeCode = tradeCode;
        this.amount = amount;
        setComments(comments);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("AccountKeepInfo")
                .add("accountId", getAccountId())
                .add("accountNo", getAccountNo())
                .add("userId", getUserId())
                .add("username", getUsername())
                .add("tradeCode", tradeCode.code() + "/" + tradeCode.message() + "/" + tradeCode.direction())
                .add("amount", amount)
                .add("accountType", getAccountType())
                .omitNullValues()
                .toString();
    }
}
