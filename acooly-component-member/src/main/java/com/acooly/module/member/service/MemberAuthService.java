package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.entity.MemberAuth;
import com.acooly.module.member.enums.AuthStatusEnum;

/**
 * 会员认证服务
 *
 * @author zhangpu
 * @date 2018-10-16 16:32
 */
public interface MemberAuthService {

    /**
     * 创建操作员
     *
     * @param memberAuthInfo
     * @return
     */
    MemberAuth create(MemberAuthInfo memberAuthInfo);

    /**
     * 变更状态
     *
     * @param memberInfo
     * @param loginId
     * @param authStatus
     * @return
     */
    MemberAuth updateStatus(MemberInfo memberInfo, String loginId, AuthStatusEnum authStatus);

    /**
     * 更新密码
     *
     * @param id
     * @param newPassword
     */
    void updatePassword(Long id, String newPassword);

    /**
     * 更新密码
     * 根据用户标志+loginId
     *
     * @param memberInfo
     * @param loginId
     * @param newPassword
     */
    void updatePassword(MemberInfo memberInfo, String loginId, String newPassword);

    /**
     * 修改密码
     *
     * @param memberInfo
     * @param loginId
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(MemberInfo memberInfo, String loginId, String oldPassword, String newPassword);


    /**
     * 主账户认证
     *
     * @param memberInfo
     * @param key
     */
    void auth(MemberInfo memberInfo, String key);

    /**
     * 子账户认证
     *
     * @param memberInfo
     * @param loginId
     * @param key
     */
    void auth(MemberInfo memberInfo, String loginId, String key);
}
