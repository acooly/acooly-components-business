/*
 * www.acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2016-10-27 23:31 创建
 */
package com.acooly.module.member;

import com.acooly.module.member.enums.MemberTemplateEnum;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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

    /**
     * memeber profile default avatar path
     */
    private String defaultAvatar = "/assets/default_avatar@64.png";


    /**
     * 管理相关参数
     */
    private Manage manage = new Manage();

    /**
     * 管理功能参数控制
     */
    @Getter
    @Setter
    public static class Manage {
        /**
         * 允许后台创建会员
         */
        private boolean allowCreate = true;

    }


    private Auth auth = new Auth();

    /**
     * 账号认证参数
     */
    @Getter
    @Setter
    public static class Auth {

        /**
         * 是否开启同名用户登录互斥 开关 [未实现]
         */
        private boolean conflict = false;

        /**
         * 密码错误次数锁定 开关
         */
        private boolean lock = true;

        /**
         * 锁定：失败次数
         */
        private long lockFailTimes = 5;
        /**
         * 锁定：分钟数
         */
        private long lockMinutes = 60;


        /**
         * 是否开启密码过期处理 开关
         */
        private boolean passwordExpire = true;

        /**
         * 账号有效期, 默认90天
         */
        private int passwordExpireDays = 90;
        /**
         * 密码格式组成规则
         * <p>
         * [a-zA-Z]{1}[\\\\w]{7,15}  密码必须以字母开头，由字母、数字、下划线组成，长度8-16字节。
         */
        private String passwordRegex = "[\\\\w]{6,16}";
        /**
         * 密码格式错误提示
         */
        private String passwordError = "密码由任意字母、数字、下划线组成，长度6-16字节";

    }


    @Override
    public void afterPropertiesSet() {
        smsTemplateDefValue(MemberTemplateEnum.common, "您本次{action}的验证码是：${captcha}, 用户名：${username}。");
        smsTemplateDefValue(MemberTemplateEnum.register, "您注册的激活验证码是：${captcha}, 用户名：${username}。");
        smsTemplateDefValue(MemberTemplateEnum.registerQuick, "您注册的激活验证码是：${captcha}, 用户名：${username}。");
        smsTemplateDefValue(MemberTemplateEnum.active, "您的会员账号：${username}已成功激活。");
        smsTemplateDefValue(MemberTemplateEnum.changePassword, "您本次修改密码的验证码是：${captcha}, 用户名：${username}。");

        // 这里模板配置的是ftl的文件名，目录在 /resource/mail/*.ftl
        mailTemplateDefValue(MemberTemplateEnum.common, "member_common");
        mailTemplateDefValue(MemberTemplateEnum.register, "member_active_demo");
        mailTemplateDefValue(MemberTemplateEnum.active, "member_active_success");
        mailTemplateDefValue(MemberTemplateEnum.changePassword, "member_changePassword");
    }

    private void smsTemplateDefValue(MemberTemplateEnum key, String defaultValue) {
        if (smsTemplates.get(key.code()) == null) {
            smsTemplates.put(key.code(), defaultValue);
        }
    }

    private void mailTemplateDefValue(MemberTemplateEnum key, String defaultValue) {
        if (mailTemplates.get(key.code()) == null) {
            mailTemplates.put(key.code(), defaultValue);
        }
    }

}
