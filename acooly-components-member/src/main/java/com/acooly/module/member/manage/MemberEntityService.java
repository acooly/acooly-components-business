/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 *
 */
package com.acooly.module.member.manage;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.member.entity.Member;
import org.apache.ibatis.annotations.Param;

/**
 * 会员信息 Service接口
 *
 * Date: 2018-07-23 00:05:26
 * @author acooly
 *
 */
public interface MemberEntityService extends EntityService<Member> {

    Member findUniqueByUserNo(String userNo);


    Member findUniqueByUsername(String username);

}
