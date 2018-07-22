/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.acooly.module.member.dao.MemberPersonalDao;
import com.acooly.module.member.entity.MemberPersonal;

/**
 * 会员个人信息 Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-07-23 00:05:26
 *
 * @author acooly
 *
 */
@Service("memberPersonalEntityService")
public class MemberPersonalEntityServiceImpl extends EntityServiceImpl<MemberPersonal, MemberPersonalDao> implements MemberPersonalEntityService {

}
