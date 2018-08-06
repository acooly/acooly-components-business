package com.acooly.module.member.service.interceptor;

import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.dto.MailSendInfo;

import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-08-05 21:46
 */
public interface MemberCaptchaInterceptor {


    /**
     * 根据配置发送注册短信验证码时的模板数据扩展。
     * <p>
     * 默认组件以在数据中提供：username, captcha
     * 如果你的注册短信验证码模板新增了其他数据，则请实现该方法扩展.
     * 注册短信的模板配置：acooly.member.active.smsTemplateContent
     *
     * @param member   被发送的用户
     * @param busiType 发送的业务（注册，重置密码等）
     * @param data     模板数据（需要回填）
     * @return 模板名称
     */
    String onCaptchaSMS(Member member, String busiType, Map<String, Object> data);


    MailSendInfo onCaptchaMail(Member member, String busiType, Map<String, Object> data);

}
