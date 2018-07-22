/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.entity.MemberProfile;
import com.acooly.module.member.enums.ProfilePhotoTypeEnum;
import com.acooly.module.member.manage.MemberProfileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会员业务信息 管理控制器
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberProfile")
public class MemberProfileManagerController extends AbstractJQueryEntityController<MemberProfile, MemberProfileEntityService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private MemberProfileEntityService memberProfileEntityService;


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allProfilePhotoTypes", ProfilePhotoTypeEnum.mapping());
        model.put("allRealNameStatuss", WhetherStatus.mapping());
        model.put("allMobileNoStatuss", WhetherStatus.mapping());
        model.put("allEmailStatuss", WhetherStatus.mapping());
        model.put("allSmsSendStatuss", WhetherStatus.mapping());
    }

}
