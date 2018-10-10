/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.enums.ProfilePhotoTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 会员业务信息 Entity
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Getter
@Setter
@Entity
@Table(name = "b_member_profile")
public class MemberProfile extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


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
     * 昵称
     */
    @Size(max = 32)
    private String nickname;

    /**
     * 个性签名
     */
    @Size(max = 255)
    private String dailyWords;

    /**
     * 头像类型
     */
    @Enumerated(EnumType.STRING)
    private ProfilePhotoTypeEnum profilePhotoType = ProfilePhotoTypeEnum.builtIn;

    /**
     * 头像
     */
    @Size(max = 255)
    private String profilePhoto;

    /**
     * 实名认证
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus realNameStatus = WhetherStatus.no;

    /**
     * 手机认证
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus mobileNoStatus = WhetherStatus.no;

    /**
     * 邮箱认证
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus emailStatus = WhetherStatus.no;

    /**
     * 发送短信
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus smsSendStatus = WhetherStatus.no;

    /**
     * 安全问题设置状态
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus secretQaStatus = WhetherStatus.no;


    /**
     * 客戶经理
     * 一般是后端操作员(sys_user)
     */
    private String manager;

    /**
     * 经纪人
     * 一般也是会员，会有持续跟进和服务
     */
    private String broker;


    /**
     * 邀请人
     * 一般也是会员，指推荐，介绍和邀请的含义
     */
    private String inviter;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


}
