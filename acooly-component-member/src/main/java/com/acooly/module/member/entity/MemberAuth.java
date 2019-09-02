/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-16
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.Strings;
import com.acooly.module.member.enums.AuthRoleEnum;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.enums.AuthTypeEnum;
import com.acooly.module.member.enums.LoginStatusEnum;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * b_member_auth Entity
 *
 * @author acooly
 * Date: 2018-10-16 15:57:37
 */
@Entity
@Table(name = "b_member_auth")
@Getter
@Setter
public class MemberAuth extends AbstractEntity {

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

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
     * 认证类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthTypeEnum authType = AuthTypeEnum.password;

    /**
     * 认证角色
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthRoleEnum authRole;

    /**
     * 登录ID
     */
    @NotEmpty
    @Size(max = 32)
    private String loginId;

    /**
     * 登录秘钥/密码
     */
    @NotEmpty
    @Size(max = 256)
    private String loginKey;

    /**
     * 认证加盐
     */
    @Size(max = 32)
    private String loginSalt;

    /**
     * 密码更新时间
     */
    private Date loginKeyUpdateTime;

    /**
     * 有效期
     */
    private Date expireTime;

    /**
     * 解锁时间
     */
    private Date unlockTime;

    /**
     * 登录失败次数
     */
    private Integer loginFailTimes = 0;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 是否登录
     */
    @Enumerated(EnumType.STRING)
    private LoginStatusEnum loginStatus = LoginStatusEnum.notlogin;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private AuthStatusEnum authStatus = AuthStatusEnum.enable;

    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;

    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getId() != null) {
            sb.append("ID:").append(getId()).append(",");
        }
        if (getUserId() != null) {
            sb.append("userID:").append(getUserId()).append(",");
        }
        if (Strings.isNotBlank(getUserNo())) {
            sb.append("userNo:").append(getUserNo()).append(",");
        }
        if (Strings.isNotBlank(getUsername())) {
            sb.append("username:").append(getUsername()).append(",");
        }
        sb.append("loginId:").append(getLoginId()).append(",");
        sb.append("status:").append(getAuthStatus()).append(",");
        return sb.substring(0, sb.length() - 1) + "}";
    }

}
