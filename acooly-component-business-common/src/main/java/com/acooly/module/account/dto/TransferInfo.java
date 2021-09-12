package com.acooly.module.account.dto;

import com.acooly.core.common.facade.InfoBase;
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
public class TransferInfo extends InfoBase {

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
     * 外部订单号
     * 如果该字段传入，则from和to中的merchOrderNo可不传入，优先级内部高于外部。
     */
    private String merchOrderNo;

    /**
     * 业务订单号
     * 内部订单号废弃，应该采用from和to内部的bizOrderNo标记唯一内部记账编码，merchOrderNo也同理
     * 一般情况下，转账业务中，merchOrderNo在from和to中可能相同，但bizOrderNo应该不同
     */
    @Deprecated
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


    public TransferInfo(String accountNoFrom, String accountNoTo, TradeCode tradeCodeFrom, TradeCode tradeCodeTo, Money amount) {
        this.from = new AccountInfo(accountNoFrom);
        this.to = new AccountInfo(accountNoTo);
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

    public TransferInfo(String accountNoFrom, String accountNoTo, Money amount) {
        this(accountNoFrom, accountNoTo, CommonTradeCodeEnum.transfer_out, CommonTradeCodeEnum.transfer_in, amount);
    }
}
