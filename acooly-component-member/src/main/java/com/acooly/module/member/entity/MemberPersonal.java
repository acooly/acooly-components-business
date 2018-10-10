/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.enums.Gender;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.enums.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

/**
 * 会员个人信息 Entity
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Getter
@Setter
@Entity
@Table(name = "b_member_personal")
public class MemberPersonal extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * 用户编码
     */
    @NotEmpty
    @Size(max = 64)
    private String userNo;

    /**
     * 用户名
     */
    @NotEmpty
    @Size(max = 32)
    private String username;


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

    /**
     * 认证状态
     */
    private CertStatusEnum certStatus = CertStatusEnum.no;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    private Gender gender;


    /**
     * 婚姻状况
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus maritalStatus;

    /**
     * 子女情况
     */
    private int childrenCount;

    /**
     * 教育背景
     */
    @Enumerated(EnumType.STRING)
    private EducationLevelEnum educationLevel;

    /**
     * 月收入
     */
    @Enumerated(EnumType.STRING)
    private IncomeMonthEnum incomeMonth;

    /**
     * 社会保险
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus socialInsurance;

    /**
     * 公积金
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus houseFund;

    /**
     * 住房情况
     */
    @Enumerated(EnumType.STRING)
    private HouseStatueEnum houseStatue;

    /**
     * 是否购车
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus carStatus;


    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;


    /**
     * 根据生日计算年龄
     * <p>
     *
     * @return
     */
    public long getAge() {
        if (this.birthday == null) {
            return 0;
        }

        return Dates.sub(new Date(), this.birthday, Calendar.YEAR);

    }

}
