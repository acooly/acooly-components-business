/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by acooly
* date:2022-02-22
*/
package com.acooly.module.point.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.point.enums.PointRuleValidTypeTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 积分规则配置 Entity
 *
 * @author acooly Date: 2022-02-22 17:32:57
 */
@Entity
@Table(name = "pt_point_rule")
@Getter
@Setter
public class PointRule extends AbstractEntity {

	/** 规则标题 */
	@Size(max = 32)
	private String title;

	/** 规则编码 */
	private String busiTypeCode;

	/** 规则描述 */
	private String busiTypeText;

	/** 金额 */
	private Long amount;

	/** 积分 */
	private Long point;

	/** 有效期 */
	@Enumerated(EnumType.STRING)
	private PointRuleValidTypeTypeEnum validType = PointRuleValidTypeTypeEnum.no_time_limit;

	/** 状态 */
	@Enumerated(EnumType.STRING)
	private AbleStatus status;

	/** 规则描述 */
	@Size(max = 255)
	private String comments;

}
