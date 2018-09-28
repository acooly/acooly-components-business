package com.acooly.module.account.dto;

import com.acooly.core.utils.Money;
import com.acooly.module.account.TradeCode;
import com.acooly.module.account.enums.CommonTradeCodeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 转账DTO
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-05 17:37
 */
@Getter
@Setter
public class TransferInfo {

    /**
     * 转出方
     */
    @NotNull
    private AccountInfo from;

    /**
     * 转入方
     */
    @NotNull
    private AccountInfo to;

    /**
     * 记账金额
     */
    @NotNull
    private Money amount;

    /**
     * 业务订单号
     */
    private String bizOrderNo;

    /**
     * 出记账类型
     */
    private TradeCode tradeCodeFrom;

    /**
     * 入记账类型
     */
    private TradeCode tradeCodeTo;


    private String batchNo;


    private String comments;


    public TransferInfo() {
    }

    /**
     * 完整构造
     *
     * @param from
     * @param to
     * @param tradeCodeFrom
     * @param tradeCodeTo
     * @param amount
     */
    public TransferInfo(AccountInfo from, AccountInfo to, TradeCode tradeCodeFrom, TradeCode tradeCodeTo, Money amount) {
        this.from = from;
        this.to = to;
        this.tradeCodeFrom = tradeCodeFrom;
        this.tradeCodeTo = tradeCodeTo;
        this.amount = amount;
    }


    public TransferInfo(Long accountIdFrom, Long accountIdTo, TradeCode tradeCodeFrom, TradeCode tradeCodeTo, Money amount) {
        this.from = new AccountInfo(accountIdFrom);
        this.to = new AccountInfo(accountIdTo);
        this.tradeCodeFrom = tradeCodeFrom;
        this.tradeCodeTo = tradeCodeTo;
        this.amount = amount;
    }

    /**
     * 快捷 通用单笔转账
     * <p>
     * 交易码内置：transfer_in, transfer_out
     *
     * @param accountIdFrom
     * @param accountIdTo
     * @param amount
     */
    public TransferInfo(Long accountIdFrom, Long accountIdTo, Money amount) {
        this(accountIdFrom, accountIdTo, CommonTradeCodeEnum.transfer_out, CommonTradeCodeEnum.transfer_in, amount);
    }


}
