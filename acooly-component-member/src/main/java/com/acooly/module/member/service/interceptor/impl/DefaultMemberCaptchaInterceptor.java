package com.acooly.module.member.service.interceptor.impl;

import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.MemberCaptchaInterceptor;
import com.acooly.module.member.service.interceptor.dto.MailSendInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 会员验证码发送默认实现
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-05 21:57
 */
@Component
public class DefaultMemberCaptchaInterceptor implements MemberCaptchaInterceptor {


    @Override
    public String onCaptchaSMS(Member member, String busiType, Map<String, Object> data) {
        return null;
    }

    @Override
    public MailSendInfo onCaptchaMail(Member member, String busiType, Map<String, Object> data) {
        return null;
    }
}
