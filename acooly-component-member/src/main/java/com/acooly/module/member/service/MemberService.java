package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberStatusEnum;

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
     * 发送激活短信或邮件
     *
     * @param username
     * @param memberActiveType
     */
    void activeSend(String username, MemberActiveTypeEnum memberActiveType);

    /**
     * 激活（ID）
     *
     * @param memberId
     * @param activeValue
     * @param memberActiveType
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
     * 会员状态管理
     *
     * @param memberInfo
     * @param memberStatus
     */
    void statusChange(MemberInfo memberInfo, MemberStatusEnum memberStatus);


}
