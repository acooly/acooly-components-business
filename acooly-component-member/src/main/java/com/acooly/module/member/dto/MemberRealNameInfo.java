/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-27 14:58 创建
 */
package com.acooly.module.member.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * todo:待完成，区分个人和企业
 *
 * @author zhangpu 2018-07-27 14:58
 */
@Getter
@Setter
public class MemberRealNameInfo extends InfoBase {

    /**
     * 会员ID
     */
    @NotNull
    private Long id;

    private PersonalRealNameInfo personalRealNameInfo;

    public MemberRealNameInfo() {

    }

    public MemberRealNameInfo(Long id, String realName, String certNo) {
        this.id = id;
        this.personalRealNameInfo = new PersonalRealNameInfo(realName, certNo);
    }
}
