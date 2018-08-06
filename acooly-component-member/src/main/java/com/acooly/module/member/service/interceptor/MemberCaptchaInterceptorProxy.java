package com.acooly.module.member.service.interceptor;

import com.acooly.integration.bean.AbstractSpringProxyBean;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.dto.MailSendInfo;
import com.acooly.module.member.service.interceptor.impl.DefaultMemberCaptchaInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代理获取集成系统的MemberRegistryInterceptor实现
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-28 23:38
 */
@Component("memberCaptchaInterceptor")
public class MemberCaptchaInterceptorProxy extends AbstractSpringProxyBean<MemberCaptchaInterceptor, DefaultMemberCaptchaInterceptor> implements MemberCaptchaInterceptor {

    @Override
    public String onCaptchaSMS(Member member, String busiType, Map<String, Object> data) {
        return getTarget().onCaptchaSMS(member, busiType, data);
    }

    @Override
    public MailSendInfo onCaptchaMail(Member member, String busiType, Map<String, Object> data) {
        return getTarget().onCaptchaMail(member, busiType, data);
    }
}
