/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.module.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * @author zhangpu
 * @date 2018-07-10
 */
@ConfigurationProperties(MemberProperties.PREFIX)
@Data
public class MemberProperties {

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
    private boolean inviterMustBeMember = true;

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


    private Active active = new Active();


    /**
     * 激活相关配置
     */
    @Getter
    @Setter
    public static class Active {

        /**
         * 激活验证码有效时长（秒）;短信和邮件公用
         */
        private long captchaTimeoutSeconds = 600;

        /**
         * 激活短信模板内容
         */
        private String smsTemplateContent = "你本次注册的激活验证码是：${captcha}, 用户名：${username}。";

        /**
         * 激活邮件主题
         */
        private String mailSubject = "注册激活邮件";

        /**
         * 激活邮件模板名称（/resources/mail/*.ftl的文件名）
         */
        private String mailTemplateName = "member_active_demo";


    }

}
