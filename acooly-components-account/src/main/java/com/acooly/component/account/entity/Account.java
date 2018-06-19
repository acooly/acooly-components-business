/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.component.account.entity;


import com.acooly.component.account.enums.AccountTypeEnum;
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

/**
 * 账户信息 Entity
 *
 * @author acooly
 * Date: 2018-06-06 10:40:46
 */
@Getter
@Setter
@Entity
@Table(name = "ac_account")
public class Account extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(max = 32)
    private String accountNo;

    /**
     * 账户类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountTypeEnum accountType = AccountTypeEnum.main;

    /**
     * 用户编号，外部集成环境用户/客户标志
     */
    @NotEmpty
    @Size(max = 64)
    private String userName;

    /**
     * 用户ID，外部集成环境用户/客户标志，可选提高性能
     */
    private Long userId;

    /**
     * 余额
     */
    @NotNull
    private Long balance;

    /**
     * 冻结金额
     */
    @NotNull
    private Long freeze;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AbleStatus status;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
