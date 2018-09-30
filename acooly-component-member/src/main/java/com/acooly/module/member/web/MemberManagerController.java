/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberGradeEnum;
import com.acooly.module.member.enums.MemberStatusEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.service.MemberService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 会员信息 管理控制器
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/member")
public class MemberManagerController extends AbstractJQueryEntityController<Member, MemberEntityService> {

    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private MemberEntityService memberEntityService;

    @Autowired
    private MemberService memberService;


    @Override
    protected Member doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate) throws Exception {

        if (isCreate) {
            MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
            bindNotValidator(request, memberRegistryInfo);
            if (Strings.isBlank(memberRegistryInfo.getPassword())) {
                // 初始化一个谁都不知道的密码
                memberRegistryInfo.setPassword(RandomStringUtils.randomAlphanumeric(8));
            }
            return memberService.register(memberRegistryInfo);
        }

        return super.doSave(request, response, model, isCreate);
    }


    @Override
    protected PageInfo<Member> doList(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        Map<String, Boolean> sorts = this.getSortMap(request);
        if(sorts.isEmpty()){
            sorts.put("id",false);
        }

        return getEntityService().queryMapper(this.getPageInfo(request), this.getSearchParams(request), this.getSortMap(request));
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allUserTypes", MemberUserTypeEnum.mapping());
        model.put("allGrades", MemberGradeEnum.mapping());
        model.put("allStatuss", MemberStatusEnum.mapping());
        model.put("allActiveTypes", MemberActiveTypeEnum.mapping());
        model.put("allWhtherStatuss",WhetherStatus.mapping());
    }

}
