package com.acooly.module.member.service.auth;

import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.entity.MemberAuth;

/**
 * @author zhangpu
 * @date 2018-10-30 16:44
 */
public interface MemberAuthenticator {

    /**
     * 认证接口
     *
     * @param memberAuthInfo
     * @return
     */
    MemberAuth authentication(MemberAuthInfo memberAuthInfo);


}
