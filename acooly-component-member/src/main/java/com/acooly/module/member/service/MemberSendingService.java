package com.acooly.module.member.service;

import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.member.enums.SendTypeEnum;

import javax.annotation.Nullable;

/**
 * 会员消息发送服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 14:42
 */
public interface MemberSendingService {


    void send(String username, Messageable action, SendTypeEnum sendType, @Nullable String target);

    void send(String username, Messageable action, SendTypeEnum sendType);

    /**
     * 证码发送
     *
     * @param action   业务动作(如：RESET_PASSWORD:找回密码)
     * @param username
     * @param sendType
     */
    void captchaSend(String username, Messageable action, SendTypeEnum sendType, @Nullable String target);

    void captchaSend(String username, Messageable action, SendTypeEnum sendType);

    /**
     * 证码验证
     *
     * @param action       业务动作
     * @param username
     * @param captchaValue
     */
    void captchaVerify(String username, Messageable action, SendTypeEnum sendType, @Nullable String target, String captchaValue);

    void captchaVerify(String username, Messageable action, SendTypeEnum sendType, String captchaValue);
}
