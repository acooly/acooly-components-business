/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 安全问题 Entity
 *
 * @author acooly
 * Date: 2018-07-23 00:05:27
 */
@Getter
@Setter
@Entity
@Table(name = "b_member_secret_qa")
public class MemberSecretQa extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @NotNull
    private Long userid;

    /**
     * 用户编码
     */
    @NotEmpty
    @Size(max = 64)
    private String userNo;

    /**
     * 用户名
     */
    @NotEmpty
    @Size(max = 32)
    private String username;

    /**
     * 问题编码
     */
    @NotEmpty
    @Size(max = 32)
    private String questionCode;

    /**
     * 问题
     */
    @NotEmpty
    @Size(max = 128)
    private String question;

    /**
     * 答案
     */
    @Size(max = 128)
    private String answer;


    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
