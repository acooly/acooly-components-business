/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 14:58 创建
 */
package com.acooly.module.member.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * todo:待完成，区分个人和企业
 *
 * @author zhangpu 2018-07-27 14:58
 */
@Getter
@Setter
public class MemberRealNameInfo {

    private Long id;

    private String realName;

    private String idCardNo;


}
