/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-06-06
 */
package com.acooly.module.account.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.SimpleStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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


    /**
     * 账户唯一业务标志
     */
    @NotEmpty
    @Size(max = 64)
    private String accountNo;

    /**
     * 用户ID，外部集成环境用户/客户唯一标志
     */
    @NotNull
    private Long userId;


    /**
     * 用户唯一业务标志
     */
    @NotEmpty
    @Size(max = 64)
    private String userNo;

    /**
     * 用户名称（冗余）
     */
    @Size(max = 64)
    private String username;

    /**
     * 账户类型
     */
    private String accountType;


    /**
     * 余额
     */
    @NotNull
    private Long balance = 0L;

    /**
     * 冻结金额
     */
    @NotNull
    private Long freeze = 0L;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private SimpleStatus status = SimpleStatus.enable;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


    @Transient
    public long getAvailable() {
        return this.balance - this.freeze;
    }

    @Transient
    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        if (getId().equals(getUserId()) && getAccountNo().equals(getUserNo())) {
            sb.append("{acct-user:").append(getId()).append("/").append(getAccountNo()).append("}");
        } else {
            sb.append("{acct:").append(getId()).append("/").append(getAccountNo()).append(",");
            sb.append("user:").append(getUserId()).append("/").append(getUserNo()).append("}");
        }
        return sb.toString();
    }

}
