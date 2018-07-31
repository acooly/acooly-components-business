/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-31
 */
package com.acooly.module.member.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.member.manage.MemberEnterpriseService;
import com.acooly.module.member.dao.MemberEnterpriseDao;
import com.acooly.module.member.entity.MemberEnterprise;

/**
 * 企业客户实名认证 Service实现
 *
 * Date: 2018-07-31 20:01:45
 *
 * @author acooly
 *
 */
@Service("memberEnterpriseService")
public class MemberEnterpriseServiceImpl extends EntityServiceImpl<MemberEnterprise, MemberEnterpriseDao> implements MemberEnterpriseService {

}
