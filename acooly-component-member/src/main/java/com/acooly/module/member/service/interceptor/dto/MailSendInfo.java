package com.acooly.module.member.service.interceptor.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 14:36
 */
@Getter
@Setter
public class MailSendInfo {

    private String templateName;

    private String title;

    public MailSendInfo() {
    }

    public MailSendInfo(String title, String templateName) {
        this.templateName = templateName;
        this.title = title;
    }
}
