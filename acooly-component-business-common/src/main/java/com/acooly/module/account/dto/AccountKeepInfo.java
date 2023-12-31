package com.acooly.module.account.dto;

import com.acooly.core.utils.Money;
import com.acooly.module.account.TradeCode;
import com.acooly.module.account.enums.DirectionEnum;
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

    /**
     * 以ID方式操作账户
     *
     * @param accountId
     * @param tradeCode
     * @param amount
     * @param busiId
     * @param busiData
     */
    @Deprecated
    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount, Long busiId, String busiData) {
        this.setAccountId(accountId);
        this.tradeCode = tradeCode;
        this.amount = amount;
        this.busiId = busiId;
        this.busiData = busiData;
    }

    @Deprecated
    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount) {
        this.setAccountId(accountId);
        this.setTradeCode(tradeCode);
        this.setAmount(amount);
    }

    @Deprecated
    public AccountKeepInfo(Long accountId, TradeCode tradeCode, Money amount, String comments) {
        this.setAccountId(accountId);
        this.setTradeCode(tradeCode);
        this.setAmount(amount);
        setComments(comments);
    }


    /**
     * 以accountNo编码方式操作账户
     *
     * @param accountNo
     * @param tradeCode
     * @param amount
     * @param busiId
     * @param busiData
     */
    public AccountKeepInfo(String accountNo, TradeCode tradeCode, Money amount, Long busiId, String busiData) {
        super(accountNo, null, null, null, null);
        this.tradeCode = tradeCode;
        this.amount = amount;
        this.busiId = busiId;
        this.busiData = busiData;
    }

    public AccountKeepInfo(String accountNo, TradeCode tradeCode, Money amount) {
        this(accountNo, tradeCode, amount, null, null);
    }

    public AccountKeepInfo(String accountNo, TradeCode tradeCode, Money amount, String comments) {
        this(accountNo, tradeCode, amount, null, null);
        setComments(comments);
    }

    public AccountKeepInfo(AccountInfo accountInfo, TradeCode tradeCode, Money amount, String comments) {
        this.setAccountId(accountInfo.getAccountId());
        this.setAccountNo(accountInfo.getAccountNo());
        this.setUserId(accountInfo.getUserId());
        this.setUserNo(accountInfo.getUserNo());
        this.setAccountType(accountInfo.getAccountType());
        this.tradeCode = tradeCode;
        this.amount = amount;
        this.setBizOrderNo(accountInfo.getBizOrderNo());
        this.setMerchOrderNo(accountInfo.getMerchOrderNo());
        setComments(comments);
    }


    public long getSymbolAmount() {
        if (this.tradeCode.direction() == DirectionEnum.keep) {
            return 0;
        }
        return this.tradeCode.direction() == DirectionEnum.in ? this.amount.getCent() : (this.amount.getCent() * -1);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("AccountKeepInfo")
                .add("merchOrderNo", getMerchOrderNo())
                .add("bizOrderNo", getBizOrderNo())
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
