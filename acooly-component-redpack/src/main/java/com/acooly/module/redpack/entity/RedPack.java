/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;
import com.acooly.module.redpack.enums.RedPackStatusEnum;

/**
 * 红包 Entity
 *
 * @author cuifuq
 * Date: 2019-03-05 18:18:09
 */
@Entity
@Table(name = "red_red_pack")
@Getter
@Setter
public class RedPack extends AbstractEntity {

	/** 名称 */
	@NotEmpty
	@Size(max=64)
    private String title;

	/** 用户id */
	@NotNull
    private Long sendUserId;

	/** 用户名称 */
	@Size(max=64)
    private String sendUserName;

	/** 状态 */
    @Enumerated(EnumType.STRING)
	@NotNull
    private RedPackStatusEnum status=RedPackStatusEnum.INIT;

	/** 过期时间 */
    private Date overdueTime;

	/** 红包总金额 */
    private Long totalAmount = 0l;

	/** 已发送金额 */
    private Long sendOutAmount = 0l;

	/** 退款金额 */
    private Long refundAmount = 0l;

	/** 总数 */
    private Long totalNum = 0l;

	/** 已发送数量 */
    private Long sendOutNum = 0l;
    
	/** 可以参与次数 */
    private Long partakeNum = 1l;

	/** 红包备注 */
	@Size(max=64)
    private String remark;

	/** 业务id */
	@Size(max=64)
    private String businessId;

	/** 业务参数 */
	@Size(max=255)
    private String businessData;

	/** 备注 */
	@Size(max=64)
    private String comments;

}
