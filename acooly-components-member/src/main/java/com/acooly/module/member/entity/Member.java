/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
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
 * 会员信息 Entity
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Getter
@Setter
@Entity
@Table(name = "b_member")
public class Member extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * 父会员ID
     */
    private Long parentid;

    /**
     * 父会员编码
     */
    @Size(max = 64)
    private String parentUserNo;

    /**
     * 会员编码
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
     * 密码
     */
    @NotEmpty
    @Size(max = 256)
    private String password;

    /**
     * 密码盐
     */
    @NotEmpty
    @Size(max = 64)
    private String salt;

    /**
     * 用户类型
     */
    @NotNull
    private MemberUserTypeEnum userType = MemberUserTypeEnum.personal;

    /**
     * 手机号码
     */
    @Size(max = 16)
    private String mobileNo;

    /**
     * 邮件
     */
    @Size(max = 128)
    private String email;

    /**
     * 姓名
     */
    @Size(max = 16)
    private String realName;

    /**
     * 身份证号码
     */
    @Size(max = 32)
    private String idCardNo;

    /**
     * 用户等级
     */
    private int grade;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private MemberStatusEnum status = MemberStatusEnum.notactive;


    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
