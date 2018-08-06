package com.acooly.module.member.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.FreeMarkers;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.captcha.Captcha;
import com.acooly.module.mail.MailDto;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberTemplateEnum;
import com.acooly.module.member.enums.SendTypeEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.AbstractMemberService;
import com.acooly.module.member.service.MemberSendingService;
import com.acooly.module.member.service.interceptor.dto.MailSendInfo;
import com.acooly.module.sms.SmsContext;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 会员发送服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 14:45
 */
@Slf4j
@Component
public class MemberSendingServiceImpl extends AbstractMemberService implements MemberSendingService {

    @Override
    public void send(String username, Messageable action, SendTypeEnum sendType, @Nullable String target) {
        doSend(username, action, sendType, false, target);
    }

    @Override
    public void send(String username, Messageable action, SendTypeEnum sendType) {
        doSend(username, action, sendType, false, null);
    }

    @Override
    public void captchaSend(String username, Messageable action, SendTypeEnum sendType, @Nullable String target) {
        doSend(username, action, sendType, true, target);
    }

    @Override
    public void captchaSend(String username, Messageable action, SendTypeEnum sendType) {
        doSend(username, action, sendType, true, null);
    }

    @Override
    public void captchaVerify(String username, Messageable action, SendTypeEnum sendType, @Nullable String target, String captchaValue) {
        try {
            Member member = loadCheckExistMember(null, null, username);
            target = getTarget(target, member, action, sendType);
            captchaService.validateCaptcha(target, action.code(), captchaValue);
            log.info("验证码验证 [成功] username:{},action:{},sendType:{}", username, action.code(), sendType.code());
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("验证码验证 [{}] 验证失败", e);
            throw new MemberOperationException(MemberErrorEnum.CAPTCHA_VERIFY_ERROR);
        }
    }

    @Override
    public void captchaVerify(String username, Messageable action, SendTypeEnum sendType, String captchaValue) {
        captchaVerify(username, action, sendType, null, captchaValue);
    }


    protected void doSend(String username, Messageable action, SendTypeEnum sendType, boolean includeCaptcha, @Nullable String target) {
        try {
            Member member = loadCheckExistMember(null, null, username);
            target = getTarget(target, member, action, sendType);
            Map<String, Object> map = Maps.newHashMap();
            map.put("action", action.message());
            map.put("username", username);
            map.put("member", member);

            if (includeCaptcha) {
                Captcha captcha = doGetCaptcha(target, action.code());
                map.put("captcha", captcha.getValue());
            }

            if (sendType == SendTypeEnum.SMS) {
                String templateName = memberCaptchaInterceptor.onCaptchaSMS(member, action.code(), map);
                doSendSms(target, action, templateName, map);
            } else {
                MailSendInfo mailSendInfo = memberCaptchaInterceptor.onCaptchaMail(member, action.code(), map);
                doSendMail(target, action, mailSendInfo, map);
            }
            log.info("发送 [{}] 成功。", action.message());
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.error("发送 [{}] 失败", e);
            throw new MemberOperationException(MemberErrorEnum.CAPTCHA_SEND_ERROR);
        }
    }


    protected void doSendSms(String mobileNo, Messageable action, String templateName, Map<String, Object> map) {
        SmsContext smsContext = new SmsContext();
        if (Servlets.getRequest() != null) {
            //如果是Web请求，则保存IP
            smsContext.setClientIp(Servlets.getRequest().getRemoteAddr());
        }
        smsContext.setComments(action.code() + "/" + action.message());
        if (Strings.isNotBlank(templateName)) {
            smsService.sendByTemplate(mobileNo, templateName, map, smsContext);
        } else {
            // 如果没有返回模板名称，则使用组件内业务类型（busiType）对应的默认模板，保障组件功能内聚完整。
            smsService.send(mobileNo, FreeMarkers.rendereString(getConfigSmsTempate(action), map), smsContext);
        }
    }


    protected void doSendMail(String mail, Messageable action, MailSendInfo mailSendInfo, Map<String, Object> map) {
        MailDto mailDto = new MailDto();
        String title = "会员" + action.message();
        String templateName = getConfigMailTempate(action);
        if (mailSendInfo != null) {
            if (Strings.isNotBlank(templateName)) {
                templateName = mailSendInfo.getTemplateName();
            }
            if (Strings.isNotBlank(title)) {
                title = mailSendInfo.getTitle();
            }
        }
        mailDto.to(mail).subject(title).setParams(map);
        mailDto.templateName(templateName);
        mailService.send(mailDto);
    }

    protected Captcha doGetCaptcha(String Key, String businessCode) {
        return captchaService.getCaptcha(Key, businessCode, memberProperties.getCaptchaTimeoutSeconds());
    }


    private String getTarget(String target, Member member, Messageable action, SendTypeEnum sendType) {
        if (Strings.isBlank(target)) {
            target = (sendType == SendTypeEnum.SMS ? member.getMobileNo() : member.getEmail());
        }
        if (Strings.isBlank(target)) {
            log.warn("发送 [失败] 发送目标地址为空。username:{},action:{},sendType:{}",
                    member.getUsername(), action.code(), sendType);
            throw new MemberOperationException(MemberErrorEnum.CAPTCHA_SEND_TARGET_EMPTY);
        }
        return target;
    }

    private String getConfigSmsTempate(Messageable action) {
        String templateContent = memberProperties.getSmsTemplates().get(action.code());
        if (Strings.isBlank(templateContent)) {
            templateContent = memberProperties.getSmsTemplates().get(MemberTemplateEnum.common);
        }
        return templateContent;
    }

    private String getConfigMailTempate(Messageable action) {
        String templateContent = memberProperties.getMailTemplates().get(action.code());
        if (Strings.isBlank(templateContent)) {
            templateContent = memberProperties.getMailTemplates().get(MemberTemplateEnum.common);
        }
        return templateContent;
    }

}
