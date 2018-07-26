package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;

/**
 * 会员服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-23 00:47
 */
public interface MemberService {


    /**
     * 注册
     *
     * @param memberRegistryInfo
     * @return
     */
    Member register(MemberRegistryInfo memberRegistryInfo);

    /**
     * 激活
     *
     * @param memberId
     * @param activeValue
     */
    void active(Long memberId, String activeValue);

    void active(String username, String activeValue);


    /**
     * 登录认证
     *
     * @param username
     * @param password
     */
    void login(String username, String password);


}
