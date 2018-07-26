/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-25 18:21 创建
 */
package com.acooly.module.member.service;

import com.acooly.core.utils.FreeMarkers;
import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaService;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.sms.SmsService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 会员相关的公共服务
 *
 * @author zhangpu 2018-07-25 18:21
 */
@Slf4j
public abstract class AbstractMemberService {


    @Autowired
    protected MemberProperties memberProperties;

//    @Autowired
//    protected CertificationService certificationService;

    @Autowired
    protected SmsService smsService;

    @Autowired
    protected CaptchaService captchaService;


    /**
     * 发送短信验证码
     *
     * @param username
     * @param mobileNo
     */
    protected void doCaptchaSmsSend(String username, String mobileNo) {
        Captcha captcha = doGetCaptcha(mobileNo);
        //你本次{action}验证码是：{captcha}, 用户名：{username}。"
        Map<String, Object> map = Maps.newHashMap();
        map.put("action", "注册激活");
        map.put("captcha", captcha.getValue());
        map.put("username", username);
        String content = FreeMarkers.rendereString(memberProperties.getActiveMessageFmTemplate(), map);
        log.info("sms captcha content:{}", content);
        smsService.send(mobileNo, content);
        log.info("注册 发送激活验证码短信 成功。");
    }

    protected void doCaptchaMailSend(String username, String mail) {

    }


    protected Captcha doGetCaptcha(String Key) {
        return captchaService.getCaptcha(Key, memberProperties.getActiveCaptchaTimeoutSeconds());
    }

    /**
     * 验证验证码
     *
     * @param key
     * @param answerValue
     */
    protected void doCaptchaVerify(String key, String answerValue) {
        captchaService.validateCaptcha(key, answerValue);
    }

}
