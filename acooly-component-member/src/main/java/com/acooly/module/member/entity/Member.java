/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.ToString;
import com.acooly.module.member.enums.MemberGradeEnum;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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
    @ToString.Invisible
    @JsonIgnore
    @NotEmpty
    @Size(max = 256)
    private String password;

    /**
     * 密码盐
     */
    @ToString.Invisible
    @JsonIgnore
    @NotEmpty
    @Size(max = 64)
    private String salt;

    /**
     * 用户类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
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
     * 真实名称
     * 个人：姓名
     * 企业：公司名称
     */
    @Size(max = 16)
    @ToString.Maskable
    private String realName;


    /**
     * 证件号码
     * 个人：身份证号码
     * 企业：社会统一代码
     */
    @Size(max = 64)
    @JsonIgnore
    @ToString.Maskable
    private String certNo;


    /**
     * 用户等级
     */
    private int grade = MemberGradeEnum.normal.code();

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

    @Transient
    public String getCertNoMask() {
        return Strings.isBlank(this.certNo)?this.certNo:Strings.maskBankCardNo(this.certNo);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("id:'").append(getId()).append('\'');
        sb.append(", userNo:'").append(userNo).append('\'');
        sb.append(", username:'").append(username).append('\'');
        sb.append(", userType:").append(userType);
        if (Strings.isNoneBlank(mobileNo)) {
            sb.append(", mobileNo:'").append(mobileNo).append('\'');
        }
        if (Strings.isNoneBlank(email)) {
            sb.append(", email:'").append(email).append('\'');
        }
        if (Strings.isNoneBlank(realName)) {
            sb.append(", realName:'").append(realName).append('\'');
        }
        if (Strings.isNoneBlank(certNo)) {
            sb.append(", certNo:'").append(certNo).append('\'');
        }
        sb.append(", grade:").append(grade);
        sb.append(", status:").append(status);
        sb.append('}');
        return sb.toString();
    }
}
