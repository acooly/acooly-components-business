/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-30 20:06 创建
 */
package com.acooly.module.member.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.module.member.enums.CertTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 个人实名信息
 *
 * @author zhangpu 2018-07-30 20:06
 */
@Getter
@Setter
public class PersonalRealNameInfo extends InfoBase {


    /**
     * 姓名
     */
    @Size(max = 32)
    private String realName;

    /**
     * 证件类型
     */
    @Enumerated(EnumType.STRING)
    private CertTypeEnum certType = CertTypeEnum.ID;

    /**
     * 证件号码
     */
    @Size(max = 64)
    private String certNo;

    /**
     * 证件有效期
     */
    private Date certValidityDate;

    /**
     * 证件正面照片
     */
    private String certFrontPath;
    /**
     * 证件背面照片
     */
    private String certBackPath;

    /**
     * 证件手持照片
     */
    private String certHoldPath;

    public PersonalRealNameInfo() {
    }

    public PersonalRealNameInfo(String realName, String certNo) {
        this.realName = realName;
        this.certNo = certNo;
    }
}
