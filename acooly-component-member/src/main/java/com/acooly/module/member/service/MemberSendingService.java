package com.acooly.module.member.service;

import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.member.enums.SendTypeEnum;

/**
 * 会员消息发送服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 14:42
 */
public interface MemberSendingService {


    /**
     * 直接发送
     *
     * <p>
     * 场景：
     * <li>用于在注册前要先发送短信和验证的情况。</li>
     * <li>类似修改手机号码这样，新手机的验证短信发送</li>
     *
     * <p>
     * 特性：
     * <li>不检查用户对应的还原是否存在</li>
     * <li>使用SMS和MAIL的核心模板功能发送</li>
     *
     * @param username
     * @param target
     * @param action
     * @param sendType
     * @param includeCaptcha
     */
    void send(String username, String target, Messageable action, SendTypeEnum sendType, boolean includeCaptcha);

    /**
     * 直接发送验证
     *
     * @param username
     * @param target
     * @param action
     * @param sendType
     * @param captchaValue
     */
    void captchaVerify(String username, String target, Messageable action, SendTypeEnum sendType, String captchaValue);

    /**
     * 会员发送
     *
     * @param action   业务动作(如：RESET_PASSWORD:找回密码)
     * @param username
     * @param sendType
     */
    void send(String username, Messageable action, SendTypeEnum sendType, boolean includeCaptcha);

    /**
     * 会员发送验证
     *
     * @param action       业务动作
     * @param username
     * @param captchaValue
     */
    void captchaVerify(String username, Messageable action, SendTypeEnum sendType, String captchaValue);
}
