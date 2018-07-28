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
import com.acooly.core.utils.Strings;
import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaService;
import com.acooly.module.certification.CertificationService;
import com.acooly.module.event.EventBus;
import com.acooly.module.mail.MailDto;
import com.acooly.module.mail.service.MailService;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.manage.MemberContactEntityService;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.acooly.module.member.manage.MemberProfileEntityService;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import com.acooly.module.sms.SmsService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 会员相关的公共服务
 *
 * @author zhangpu 2018-07-25 18:21
 */
@Slf4j
public abstract class AbstractMemberService {


    @Autowired
    protected MemberEntityService memberEntityService;

    @Autowired
    protected MemberContactEntityService memberContactEntityService;

    @Autowired
    protected MemberPersonalEntityService memberPersonalEntityService;

    @Autowired
    protected MemberProfileEntityService memberProfileEntityService;

    @Resource(name = "memberRegistryInterceptor")
    protected MemberRegistryInterceptor memberRegistryInterceptor;

    @Autowired
    protected MemberProperties memberProperties;

    @Autowired
    protected EventBus eventBus;

    @Autowired
    protected CertificationService certificationService;

    @Autowired
    protected SmsService smsService;

    @Autowired
    protected MailService mailService;

    @Autowired
    protected CaptchaService captchaService;


    /**
     * 发送短信验证码
     *
     * @param member
     */
    protected void doCaptchaSmsSend(Member member) {
        try {
            String username = member.getUsername();
            String mobileNo = member.getMobileNo();
            Captcha captcha = doGetCaptcha(mobileNo);
            //你本次{action}验证码是：{captcha}, 用户名：{username}。"
            Map<String, Object> map = Maps.newHashMap();
            map.put("captcha", captcha.getValue());
            map.put("username", username);
            memberRegistryInterceptor.onCaptchaSms(member, map);
            String content = FreeMarkers.rendereString(memberProperties.getActive().getSmsTemplateContent(), map);
            smsService.send(mobileNo, content);
            log.info("注册 发送激活验证码短信 成功。");
        } catch (Exception e) {
            log.warn("注册 发送激活验证码短信 失败 忽略待重新激活", e);
        }
    }

    protected void doCaptchaMailSend(Member member) {
        try {
            String username = member.getUsername();
            String mail = member.getEmail();
            Captcha captcha = doGetCaptcha(mail);
            Map<String, String> map = Maps.newHashMap();
            map.put("captcha", String.valueOf(captcha.getValue()));
            map.put("username", username);
            memberRegistryInterceptor.onCaptchaMail(member, map);
            MailDto mailDto = new MailDto();
            mailDto.to(mail).subject(memberProperties.getActive().getMailSubject()).setParams(map);
            mailDto.templateName(memberProperties.getActive().getMailTemplateName());
            mailService.send(mailDto);
            log.info("注册 发送激活验证邮件 成功。");
        } catch (Exception e) {
            log.warn("注册 发送激活验证邮件 失败 忽略待重新激活", e);
        }

    }


    protected Captcha doGetCaptcha(String Key) {
        return captchaService.getCaptcha(Key, memberProperties.getActive().getCaptchaTimeoutSeconds());
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


    /**
     * 依次尝试加载会员
     *
     * @param id
     * @param userNo
     * @param username
     * @return
     */
    protected Member loadMember(Long id, String userNo, String username) {
        if (id != null) {
            return memberEntityService.get(id);
        }

        if (Strings.isNotBlank(userNo)) {
            return memberEntityService.findUniqueByUserNo(userNo);
        }

        if (Strings.isNotBlank(username)) {
            return memberEntityService.findUniqueByUsername(username);
        }
        return null;
    }

    protected Member loadMember(Long id) {
        return loadMember(id, null, null);
    }


}
