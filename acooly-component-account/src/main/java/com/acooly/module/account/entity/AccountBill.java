/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.account.enums.DirectionEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 账户进出账 Entity
 * <p>
 * todo：考虑科目的挂接；考虑交易码的分类（出金/入金等）
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Getter
@Setter
@Entity
@Table(name = "ac_account_bill")
public class AccountBill extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(max = 64)
    private String billNo;

    /**
     * 账户ID
     */
    @NotNull
    private Long accountId;

    @NotEmpty
    @Size(max = 64)
    private String accountNo;


    @NotNull
    private Long userId;

    @NotEmpty
    @Size(max = 64)
    private String userNo;

    /**
     * 用户名（冗余）
     */
    @Size(max = 64)
    private String username;

    /**
     * 外部订单号
     */
    private String merchOrderNo;

    /**
     * 内部订单号
     */
    private String bizOrderNo;

    /**
     * 交易金额
     */
    @NotNull
    private Long amount;

    /**
     * 变动后余额
     */
    @NotNull
    private Long balancePost;

    /**
     * 资金流向
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private DirectionEnum direction;

    /**
     * 交易码
     */
    @NotEmpty
    @Size(max = 16)
    private String tradeCode;

    /**
     * 相关业务ID
     */
    private Long busiId;

    /**
     * 相关业务数据
     */
    @Size(max = 128)
    private String busiData;

    /**
     * 批量交易号
     */
    @Size(max = 64)
    private String batchNo;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AbleStatus status = AbleStatus.enable;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
