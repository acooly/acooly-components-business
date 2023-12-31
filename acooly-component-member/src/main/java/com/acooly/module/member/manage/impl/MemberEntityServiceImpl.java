/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.manage.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.BeanUtils;
import com.acooly.core.utils.Collections3;
import com.acooly.module.member.dao.MemberDao;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.manage.MemberEntityService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 会员信息 Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-07-23 00:05:26
 *
 * @author acooly
 */
@Service("memberEntityService")
public class MemberEntityServiceImpl extends EntityServiceImpl<Member, MemberDao> implements MemberEntityService {


    @Override
    public Member findUniqueByUserNo(String userNo) {
        return getEntityDao().findUniqueByUserNo(userNo);
    }

    @Override
    public Member findUniqueByUsername(String username) {
        return getEntityDao().findUniqueByUsername(username);
    }


    @Override
    public PageInfo<Member> queryMapper(PageInfo<Member> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap) {
        return getEntityDao().queryPage(pageInfo,map,sortMap);
    }



}



