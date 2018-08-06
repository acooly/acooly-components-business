/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-25 18:21 创建
 */
package com.acooly.module.member.service;

import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.Strings;
import com.acooly.module.captcha.CaptchaService;
import com.acooly.module.certification.CertificationService;
import com.acooly.module.event.EventBus;
import com.acooly.module.mail.service.MailService;
import com.acooly.module.member.MemberProperties;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.manage.MemberContactEntityService;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.acooly.module.member.manage.MemberProfileEntityService;
import com.acooly.module.member.service.interceptor.MemberCaptchaInterceptor;
import com.acooly.module.member.service.interceptor.MemberRegistryInterceptor;
import com.acooly.module.security.utils.Digests;
import com.acooly.module.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 会员相关的公共服务
 *
 * @author zhangpu 2018-07-25 18:21
 */
@Slf4j
public abstract class AbstractMemberService {


    public static final int HASH_INTERATIONS = 512;
    public static final int SALT_SIZE = 8;

    @Autowired
    protected MemberEntityService memberEntityService;

    @Autowired
    protected MemberContactEntityService memberContactEntityService;

    @Autowired
    protected MemberPersonalEntityService memberPersonalEntityService;

    @Autowired
    protected MemberProfileEntityService memberProfileEntityService;

    @Autowired
    protected MemberSendingService memberSendingService;


    @Resource(name = "memberRegistryInterceptor")
    protected MemberRegistryInterceptor memberRegistryInterceptor;

    @Resource(name = "memberCaptchaInterceptor")
    protected MemberCaptchaInterceptor memberCaptchaInterceptor;

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


    protected void doValidatePassword(Member member, String password) {
        String dbSalt = member.getSalt();
        String enPassword = digestPassword(password, dbSalt);
        String dbPassword = member.getPassword();
        if (!Strings.equals(enPassword, dbPassword)) {
            log.warn("登录 [失败] 原因:{}, member:{}", MemberErrorEnum.LOGIN_PASSWORD_VERIFY_FAIL, member);
            throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL);
        }
    }

    protected void doDigestPassword(Member member) {
        String salt = Encodes.encodeHex(Digests.generateSalt(SALT_SIZE));
        member.setSalt(salt);
        member.setPassword(digestPassword(member.getPassword(), salt));
    }

    protected String digestPassword(String plainPassword, String salt) {
        return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
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


    protected Member loadAndCheckMember(Long id, String userNo, String username) {
        Member member = loadMember(null, null, username);
        if (member == null) {
            log.warn("加载会员 [失败] 原因:{}, username:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, username);
            throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL, username);
        }
        if (member.getStatus() != MemberStatusEnum.enable) {
            log.warn("加载会员 [失败] 原因:{}, username:{}", MemberErrorEnum.MEMEBER_STATUS_NOT_ENABLE, username);
            throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL);
        }
        return member;
    }

    protected Member loadCheckExistMember(Long id, String userNo, String username) {
        Member member = loadMember(null, null, username);
        if (member == null) {
            log.warn("加载会员 [失败] 原因:{}, username:{}", MemberErrorEnum.MEMEBER_NOT_EXIST, username);
            throw new MemberOperationException(MemberErrorEnum.LOGIN_VERIFY_FAIL, username);
        }
        return member;
    }

    protected Member loadMember(Long id) {
        return loadMember(id, null, null);
    }


}
