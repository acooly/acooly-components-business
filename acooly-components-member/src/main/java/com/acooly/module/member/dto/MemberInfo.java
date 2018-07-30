/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 17:18 创建
 */
package com.acooly.module.member.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.validate.jsr303.CertNo;
import com.acooly.core.utils.validate.jsr303.MobileNo;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zhangpu 2018-07-23 17:18
 */
@Getter
@Setter
public class MemberInfo extends InfoBase {


    private Long id;

    /**
     * 添加时可选设置，不设置为系统自动生成
     */
    @Size(max = 64)
    private String userNo;

    /**
     * 父会员ID
     */
    private Long parentid;

    @Size(max = 64)
    private String parentUserNo;

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
     * 用户类型
     */
    @NotNull
    private MemberUserTypeEnum userType = MemberUserTypeEnum.personal;

    /**
     * 手机号码
     */
    @MobileNo(blankable = true)
    @Size(max = 16)
    private String mobileNo;

    /**
     * 邮件
     */
    @Email
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
    @CertNo(blankable = true)
    @Size(max = 32)
    private String idCardNo;

    /**
     * 用户等级
     */
    private int grade;

    /**
     * 状态
     * 注册时，该属性无效
     */
    @Enumerated(EnumType.STRING)
    private MemberStatusEnum status = MemberStatusEnum.notactive;


    public MemberInfo() {
    }

    /**
     * for 标记用户
     *
     * @param id
     * @param userNo
     * @param username
     */
    public MemberInfo(Long id, String userNo, String username) {
        this.id = id;
        this.userNo = userNo;
        this.username = username;
    }

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getId() != null) {
            sb.append("{ID:").append(getId()).append(",");
        }
        if (Strings.isNotBlank(getUserNo())) {
            sb.append("userNo:").append(getUserNo()).append(",");
        }
        if (Strings.isNotBlank(getUsername())) {
            sb.append("username:").append(getUsername()).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }


}
