/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.member.manage.MemberSecretQaEntityService;
import com.acooly.module.member.dao.MemberSecretQaDao;
import com.acooly.module.member.entity.MemberSecretQa;

/**
 * 安全问题 Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-07-23 00:05:27
 *
 * @author acooly
 *
 */
@Service("memberSecretQaEntityService")
public class MemberSecretQaEntityServiceImpl extends EntityServiceImpl<MemberSecretQa, MemberSecretQaDao> implements MemberSecretQaEntityService {

}
