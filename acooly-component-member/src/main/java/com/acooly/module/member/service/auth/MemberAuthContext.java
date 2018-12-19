package com.acooly.module.member.service.auth;

import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.entity.MemberAuth;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2018-10-26 17:02
 */
@Getter
@Setter
@Slf4j
public class MemberAuthContext {

    private MemberAuthInfo memberAuthInfo;

    private MemberAuth memberAuth;

}
