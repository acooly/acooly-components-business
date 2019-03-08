/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-16
 */
package com.acooly.module.member.manage.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Strings;
import com.acooly.module.member.dao.MemberAuthDao;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthRoleEnum;
import com.acooly.module.member.manage.MemberAuthEntityService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * b_member_auth Service实现
 * <p>
 * Date: 2018-10-16 15:57:37
 *
 * @author acooly
 */
@Service("memberAuthEntityService")
public class MemberAuthEntityServiceImpl extends EntityServiceImpl<MemberAuth, MemberAuthDao> implements MemberAuthEntityService {


    @Override
    public List<MemberAuth> findByAuthRole(MemberInfo memberInfo, AuthRoleEnum authRoleEnum) {
        memberInfo.check();
        Map<String, Object> map = Maps.newHashMap();
        if (memberInfo.getId() != null) {
            map.put("EQ_userId", memberInfo.getId());
        }
        if (Strings.isNoneBlank(memberInfo.getUsername())) {
            map.put("EQ_username", memberInfo.getUsername());
        }

        if (authRoleEnum != null) {
            map.put("EQ_authRole", authRoleEnum);
        }
        return query(map, null);
    }


    @Override
    public MemberAuth loadMemberAuth(MemberInfo memberInfo, String loginId) {

        if (memberInfo.getId() != null) {
            return getEntityDao().findByUserIdAndLoginId(memberInfo.getId(), loginId);
        }

        if (Strings.isNotBlank(memberInfo.getUsername())) {
            return getEntityDao().findByUsernameAndLoginId(memberInfo.getUsername(), loginId);
        }
        return null;
    }


    /**
     * 更新认证结果
     * <p>
     * 新事务
     *
     * @return
     */
    @Override
    public void updateAuthResult(MemberAuth memberAuth) {
        update(memberAuth);
    }
}
