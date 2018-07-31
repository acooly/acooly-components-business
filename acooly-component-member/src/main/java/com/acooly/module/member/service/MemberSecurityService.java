package com.acooly.module.member.service;

import com.acooly.core.utils.enums.Messageable;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-29 03:37
 */
public interface MemberSecurityService {

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
     * 手机验证码发送
     *
     * @param action   业务动作(如：forgetPassword:找回密码)
     * @param username
     */
    void mobileNoCaptchaSend(Messageable action, String username);

    /**
     * 手机验证码验证
     *
     * @param action   业务动作
     * @param username
     * @param captcha
     */
    void mobileNoCaptchaVerify(Messageable action, String username, String captcha);


}
