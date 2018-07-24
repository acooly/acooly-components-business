/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 17:30 创建
 */
package com.acooly.module.member.dto;

import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberRegisterTypeEnum;
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
     * 注册方式
     *
     * <li>normal: 注册后未激活，需要走激活流程</li>
     * <li>actived: 注册后自动激活</li>
     */
    @NotNull
    private MemberRegisterTypeEnum memberRegisterType = MemberRegisterTypeEnum.normal;

    /**
     * 激活方式
     * <p>
     * 需要根据注册方式搭配使用。
     * <li>如果memberRegisterType = normal，则根据memberActiveType的方式走不同的流程</li>
     * <li>如果memberRegisterType = actived，则根据memberActiveType来验证对应的是否有值（比如如果选择的是mobileNo，则要求mobileNo不为空）</li>
     * </p>
     */
    @NotNull
    private MemberActiveTypeEnum memberActiveType = MemberActiveTypeEnum.mobileNo;


    /**
     * 是否关联开启默认账户
     * <p>
     * 该参数的优先级大于member组件的配置参数
     */
    @NotNull
    private WhetherStatus accountRegisty = WhetherStatus.no;

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

}
