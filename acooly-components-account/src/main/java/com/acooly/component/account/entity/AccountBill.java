/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.entity;


import com.acooly.component.account.enums.DirectionEnum;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.AbleStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 账户进出账 Entity
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
    @Size(max = 32)
    private String billNo;

    /**
     * 账户ID
     */
    @NotNull
    private Long accountId;

    @NotEmpty
    @Size(max = 32)
    private String accountNo;


    @NotNull
    private Long userId;

    /**
     * 用户名
     */
    @Size(max = 64)
    private String username;

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
     * 交易时间
     */
    @NotNull
    private Date tradeTime = new Date();

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
