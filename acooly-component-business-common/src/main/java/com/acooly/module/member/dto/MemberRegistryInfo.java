/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 17:30 创建
 */
package com.acooly.module.member.dto;

import com.acooly.core.utils.Strings;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author zhangpu 2018-07-23 17:30
 */
@Getter
@Setter
public class MemberRegistryInfo extends MemberInfo {
    /**
     * 用户类型
     */
    @NotNull
    private MemberUserTypeEnum memberUserType = MemberUserTypeEnum.personal;

    /**
     * 激活方式
     */
    @NotNull
    private MemberActiveTypeEnum memberActiveType = MemberActiveTypeEnum.human;


    /**
     * 是否关联开启默认账户
     * <p>
     * 该参数的优先级大于member组件的配置参数,不设置，则以member的组件配置参数为准
     */
    private Boolean accountRegisty;

    /**
     * 注册时候同步实名认证
     * <p>
     * 该参数的优先级大于member组件的配置参数,不设置，则以member的组件配置参数为准
     */
    private Boolean realNameOnRegisty;

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


    public MemberRegistryInfo() {
    }

    public MemberRegistryInfo(String usename, String password, String mobileNo, String realName, String certNo) {
        setUsername(usename);
        setPassword(password);
        setMobileNo(mobileNo);
        if (Strings.isNoneBlank(realName) && Strings.isNotBlank(certNo)) {
            this.realNameOnRegisty = true;
            setRealName(realName);
            setCertNo(certNo);
        }

    }

    public MemberRegistryInfo(String usename, String password, String mobileNo) {
        this(usename, password, mobileNo, null, null);
    }
}
