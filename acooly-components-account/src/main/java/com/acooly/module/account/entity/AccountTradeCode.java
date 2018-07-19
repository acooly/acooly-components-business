/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.entity;


import com.acooly.module.account.enums.DirectionEnum;
import com.acooly.core.common.domain.AbstractEntity;
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
 * 交易编码定义 Entity
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Getter
@Setter
@Entity
@Table(name = "ac_account_trade_code")
public class AccountTradeCode extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 交易编码
     */
    @NotEmpty
    @Size(max = 16)
    private String tradeCode;

    /**
     * 交易名称
     */
    @NotEmpty
    @Size(max = 32)
    private String tradeName;

    /**
     * 方向
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private DirectionEnum direction;

    /**
     * comments
     */
    @Size(max = 128)
    private String comments;

}
