package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;

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
     * 激活（ID）
     *
     * @param memberId
     * @param activeValue
     */
    void active(Long memberId, String activeValue, MemberActiveTypeEnum memberActiveType);

    /**
     * 激活（用户名）
     *
     * @param username
     * @param activeValue
     * @param memberActiveType
     */
    void active(String username, String activeValue, MemberActiveTypeEnum memberActiveType);


    /**
     * 登录认证
     *
     * @param username
     * @param password
     */
    void login(String username, String password);






}
