/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 17:43 创建
 */
package com.acooly.module.member.service.event;

import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.entity.MemberProfile;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu 2018-07-26 17:43
 */
@Getter
@Setter
public class MemberRegistryEvent {

    /**
     * 会员注册请求对象
     */
    private MemberRegistryInfo memberRegistryInfo;

    /**
     * 会员实体（注册后 end and after）
     */
    private Member member;

    /**
     * 会员配置信息（注册后 end and after）
     */
    private MemberProfile memberProfile;


    public MemberRegistryEvent() {
    }

    public MemberRegistryEvent(MemberRegistryInfo memberRegistryInfo, Member member, MemberProfile memberProfile) {
        this.memberRegistryInfo = memberRegistryInfo;
        this.member = member;
        this.memberProfile = memberProfile;
    }
}
