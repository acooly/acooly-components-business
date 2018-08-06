/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.module.member;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author zhangpu
 * @date 2018-07-10
 */
@ConfigurationProperties(MemberProperties.PREFIX)
@Data
public class MemberProperties implements InitializingBean {

    public static final String PREFIX = "acooly.member";
    /**
     * 是否启用
     */
    @NotNull
    private boolean enable = true;

    /**
     * 经纪人必须是会员（打开则会验证）
     */
    @NotNull
    private boolean brokerMustBeMember = true;

    /**
     * 邀请人必须是会员（打开则会验证）
     */
    @NotNull
    private boolean inviterMustBeMember = false;

    /**
     * 注册时候同步实名认证
     */
    @NotNull
    private boolean realNameOnRegistry = false;

    /**
     * 是否开启账务功能，同步开账户
     */
    @NotNull
    private boolean accountRegisty = false;

    /**
     * 验证码有效时长（秒）;短信和邮件公用
     */
    private long captchaTimeoutSeconds = 600;

    /**
     * 激活成功是否发短信
     */
    private boolean sendSmsOnActiveSuccess = true;

    /**
     * 激活成功是否发邮件
     */
    private boolean sendMailOnActiveSuccess = true;


    /**
     * 短信模板：默认情况下，可在此参数配置短信模板内容.
     */
    private Map<String, String> smsTemplates = Maps.newHashMap();

    /**
     * 这里模板配置的是ftl的文件名，目录在 /resource/mail/*.ftl
     */
    private Map<String, String> mailTemplates = Maps.newHashMap();

    @Override
    public void afterPropertiesSet() {
        smsTemplates.put("common", "你本次{action}的验证码是：${captcha}, 用户名：${username}。");
        smsTemplates.put("register", "你本次注册的激活验证码是：${captcha}, 用户名：${username}。");
        smsTemplates.put("active", "你的会员账号：${username}已成功激活。");
        smsTemplates.put("changePassword", "你本次修改密码的验证码是：${captcha}, 用户名：${username}。");

        // 这里模板配置的是ftl的文件名，目录在 /resource/mail/*.ftl
        mailTemplates.put("common", "member_common");
        mailTemplates.put("register", "member_active_demo");
        mailTemplates.put("active", "member_active_success");
        mailTemplates.put("changePassword", "member_changePassword");
    }


}
