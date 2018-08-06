package com.acooly.module.member.service;

import com.acooly.module.member.dto.MemberInfo;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-29 03:37
 */
public interface MemberSecurityService {

    /**
     * 登录认证
     *
     * @param username
     * @param password
     */
    void login(String username, String password);


    /**
     * 修改密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(String username, String oldPassword, String newPassword);


    /**
     * 重置密码
     * <p>
     * 用于前置验证已通过后，再调用重置。
     * <li>手机短信验证码验证通过，然后调用重置</li>
     * <li>安全问题回答验证通过，然后调用重置</li>
     * <li>组合验证通过，然后调用重置</li>
     *
     * @param username
     * @param newPassword
     */
    void resetPassword(String username, String newPassword);


    /**
     * 修改手机号码
     *
     * @param memberInfo
     * @param newMobileNo
     */
    void changeMobileNo(MemberInfo memberInfo, String newMobileNo);


    /**
     * 修改邮箱
     *
     * @param memberInfo
     * @param newEmail
     */
    void changeEmail(MemberInfo memberInfo, String newEmail);


}
