/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.entity.MemberPersonal;
import com.acooly.module.member.enums.EducationLevelEnum;
import com.acooly.module.member.enums.HouseStatueEnum;
import com.acooly.module.member.enums.IncomeMonthEnum;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会员个人信息 管理控制器
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberPersonal")
public class MemberPersonalManagerController extends AbstractJQueryEntityController<MemberPersonal, MemberPersonalEntityService> {

    private static Map<Integer, String> allChildrenCounts = Maps.newLinkedHashMap();

    static {
        allChildrenCounts.put(0, "无");
        allChildrenCounts.put(1, "1个");
        allChildrenCounts.put(2, "2个");
        allChildrenCounts.put(3, "3个");
        allChildrenCounts.put(4, "3个以上");
    }

    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private MemberPersonalEntityService memberPersonalEntityService;


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allMaritalStatuss", WhetherStatus.mapping());
        model.put("allChildrenCounts", allChildrenCounts);
        model.put("allEducationLevels", EducationLevelEnum.mapping());
        model.put("allIncomeMonths", IncomeMonthEnum.mapping());
        model.put("allSocialInsurances", WhetherStatus.mapping());
        model.put("allHouseFunds", WhetherStatus.mapping());
        model.put("allHouseStatues", HouseStatueEnum.mapping());
        model.put("allCarStatuss", WhetherStatus.mapping());
    }

}
