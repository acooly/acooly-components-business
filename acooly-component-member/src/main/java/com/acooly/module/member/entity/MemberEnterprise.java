/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-31
*/
package com.acooly.module.member.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.acooly.module.member.enums.*;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;

/**
 * 企业客户实名认证 Entity
 *
 * @author acooly
 * Date: 2018-07-31 20:01:45
 */
@Entity
@Table(name = "b_member_enterprise")
@Getter
@Setter
public class MemberEnterprise extends AbstractEntity {

	/** 用户编码 */
	@NotEmpty
	@Size(max=64)
    private String userNo;

	/** 用户名 */
	@Size(max=32)
    private String username;

	/** 企业类型 */
    @Enumerated(EnumType.STRING)
    private MemberUserTypeEnum entType;

	/** 企业名称 */
	@Size(max=64)
    private String entName;

	/** 社会统一信用代码 */
	@Size(max=64)
    private String licenceNo;

	/** 营业执照图片地址 */
	@Size(max=512)
    private String licencePath;

	/** 营业执照地址 */
	@Size(max=128)
    private String licenceAddress;

	/** 营业年限 */
    private Integer businessLife;

	/** 法人姓名 */
	@Size(max=64)
    private String legalName;

	/** 法人证件类型: 默认身份证 */
	@Size(max=64)
    private CertTypeEnum legalCertType = CertTypeEnum.ID;

	/** 法人证件号码 */
	@Size(max=64)
    private String legalCertNo;

	/** 法人证件到期时间 */
	@Size(max=32)
    private String legalCertValidTime;

	/** 法人证件正面图片 */
	@Size(max=512)
    private String legalCertFrontPath;

	/** 法人证件背面图片 */
	@Size(max=512)
    private String legalCertBackPath;

	/** 经营范围 */
	@Size(max=512)
    private String businessScope;

	/** 实际控股人或企业类型 */
    @Enumerated(EnumType.STRING)
    private HoldingEnumEnum holdingEnum = HoldingEnumEnum.BUSINESS;

	/** 股东或实际控制人真实姓名 */
	@Size(max=64)
    private String holdingName;

	/** 股东或实际控制人证件类型 */
    @Enumerated(EnumType.STRING)
    private CertTypeEnum holdingCertType = CertTypeEnum.ID;

	/** 股东或实际控制人证件号 */
	@Size(max=64)
    private String holdingCertNo;

	/** 股东或实际控制人证件到期时间 */
	@Size(max=32)
    private String holdingCertValidTime;

	/** 股东或实际控制人证件正面图片 */
	@Size(max=512)
    private String holdingCertFrontPath;

	/** 股东或实际控制人证件背面图片 */
	@Size(max=512)
    private String holdingCertBackPath;

	/** 开户许可证号码 */
	@Size(max=32)
    private String accountLicenseNo;

	/** 开户许可证图片 */
	@Size(max=512)
    private String accountLicensePath;

	/** 税务登记证号码 */
	@Size(max=32)
    private String taxAuthorityNo;

	/** 税务登记证图片 */
	@Size(max=512)
    private String taxAuthorityPath;

	/** 备注 */
	@Size(max=512)
    private String comments;

}
