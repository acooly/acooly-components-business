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
import javax.validation.constraints.Size;

/**
 * 客户联系信息 Entity
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Getter
@Setter
@Entity
@Table(name = "b_member_contact")
public class MemberContact extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


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
     * 手机号码
     */
    @Size(max = 32)
    private String mobileNo;

    /**
     * 电话号码
     */
    @Size(max = 32)
    private String phoneNo;

    /**
     * 居住地 省
     */
    @Size(max = 64)
    private String province;

    /**
     * 居住地 市
     */
    @Size(max = 64)
    private String city;

    /**
     * 居住地 县/区
     */
    @Size(max = 64)
    private String district;

    /**
     * 详细地址
     */
    @Size(max = 256)
    private String address;

    /**
     * 邮政编码
     */
    @Size(max = 6)
    private String zip;

    /**
     * QQ
     */
    @Size(max = 16)
    private String qq;

    /**
     * MSN
     */
    @Size(max = 32)
    private String wechat;

    /**
     * 旺旺
     */
    @Size(max = 32)
    private String wangwang;

    /**
     * 备注
     */
    @Size(max = 32)
    private String google;

    /**
     * facebeek
     */
    @Size(max = 32)
    private String facebeek;

    /**
     * email
     */
    @Size(max = 128)
    private String email;


    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
