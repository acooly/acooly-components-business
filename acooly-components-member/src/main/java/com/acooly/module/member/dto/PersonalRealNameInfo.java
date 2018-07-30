/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-30 20:06 创建
 */
package com.acooly.module.member.dto;

import com.acooly.module.member.enums.IdCardTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 个人实名信息
 *
 * @author zhangpu 2018-07-30 20:06
 */
@Getter
@Setter
public class PersonalRealNameInfo extends MemberInfo {


    /**
     * 姓名
     */
    @NotNull
    @Size(max = 32)
    private String realName;

    /**
     * 证件类型
     */
    @NotNull
    private IdCardTypeEnum idCardType = IdCardTypeEnum.ID_Card;

    /**
     * 证件号码
     */
    @NotNull
    private String idCardNo;

    /**
     * 证件有效期
     */
    private Date idCardValidityDate;

    /**
     * 证件正面照片
     */
    private String idCardFrontPath;
    /**
     * 证件背面照片
     */
    private String idCardBackPath;

    /**
     * 证件手持照片
     */
    private String idCardHoldPath;
}
