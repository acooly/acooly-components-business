/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-16
 *
 */
package com.acooly.module.member.manage;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthRoleEnum;

import java.util.List;

/**
 * b_member_auth Service接口
 * <p>
 * Date: 2018-10-16 15:57:37
 *
 * @author acooly
 */
public interface MemberAuthEntityService extends EntityService<MemberAuth> {

    /**
     * 查询会员的操作员
     *
     * @param memberInfo
     * @param authRoleEnum
     * @return
     */
    List<MemberAuth> findByAuthRole(MemberInfo memberInfo, AuthRoleEnum authRoleEnum);

    /**
     * 根据会员信息和登录名获取唯一操作员
     *
     * @param memberInfo
     * @param loginId
     * @return
     */
    MemberAuth loadMemberAuth(MemberInfo memberInfo, String loginId);

    /**
     * 更新认证结果
     *
     * @param memberAuth
     * @return
     */
    void updateAuthResult(MemberAuth memberAuth);


}
