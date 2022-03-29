/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-08-06
*/
package com.acooly.module.point.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;

/**
 * 积分业务统计 Entity
 *
 * @author acooly
 * Date: 2018-08-06 16:18:40
 */
@Entity
@Table(name = "pt_point_type_count")
@Getter
@Setter
public class PointTypeCount extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户号 */
	@NotEmpty
    private String userNo;

	/** 用户名 */
	@NotEmpty
    private String userName;

	/** 统计次数 */
	@NotNull
    private Long num = 0l;

	/** 业务类型 */
	@Size(max=32)
    private String busiType;

	/** 业务类型描述 */
	@Size(max=32)
    private String busiTypeText;

	/** 总积分 */
	@NotNull
    private Long totalPoint = 0L;

	/** 备注 */
	@Size(max=256)
    private String comments;

}
