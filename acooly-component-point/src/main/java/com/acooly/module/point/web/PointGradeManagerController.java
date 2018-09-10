/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.point.domain.PointGrade;
import com.acooly.module.point.service.PointGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 积分等级 管理控制器
 *
 * @author cuifuqiang Date: 2017-02-03 22:47:28
 */
@Controller
@RequestMapping(value = "/manage/point/pointGrade")
public class PointGradeManagerController
        extends AbstractPointManageController<PointGrade, PointGradeService> {

    @SuppressWarnings("unused")
    @Autowired
    private PointGradeService pointGradeService;

    {
        allowMapping = "*";
    }
    
    
    
}