/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 */
package com.acooly.module.member.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.dto.MemberRealNameInfo;
import com.acooly.module.member.dto.PersonalRealNameInfo;
import com.acooly.module.member.entity.MemberPersonal;
import com.acooly.module.member.enums.*;
import com.acooly.module.member.manage.MemberPersonalEntityService;
import com.acooly.module.member.service.MemberRealNameService;
import com.acooly.module.ofile.OFileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 会员个人信息 管理控制器
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberPersonal")
public class MemberPersonalManagerController extends AbstractJsonEntityController<MemberPersonal, MemberPersonalEntityService> {

    {
        allowMapping = "list,update,show";
    }

    @Autowired
    private MemberPersonalEntityService memberPersonalEntityService;
    @Autowired
    private MemberRealNameService memberRealNameService;
    @Autowired
    private OFileProperties oFileProperties;

    /**
     * 个人实名认证 视图
     */
    @RequestMapping(path = "verify", method = RequestMethod.GET)
    public String verifyViewV(HttpServletRequest request, HttpServletResponse response, Model model) {
        edit(request, response, model);
        return "/manage/component/member/memberPersonalVerify";
    }

    @RequestMapping(path = "verify", method = RequestMethod.POST)
    @ResponseBody
    public JsonEntityResult<MemberPersonal> verifyHandle(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            PersonalRealNameInfo personalRealNameInfo = new PersonalRealNameInfo();
            bindNotValidator(request, personalRealNameInfo);
            //文件上传处理
            getUploadConfig().setStorageRoot(oFileProperties.getStorageRoot());
            getUploadConfig().setStorageNameSpace("cert");
            getUploadConfig().setUseMemery(false);
            getUploadConfig().setThumbnailEnable(true);
            Map<String, UploadResult> uploadResultMap = doUpload(request);
            if (uploadResultMap != null) {
                if (uploadResultMap.get("certFront") != null) {
                    personalRealNameInfo.setCertFrontPath(uploadResultMap.get("certFront").getRelativeFile());
                }
                if (uploadResultMap.get("certBack") != null) {
                    personalRealNameInfo.setCertBackPath(uploadResultMap.get("certBack").getRelativeFile());
                }
                if (uploadResultMap.get("certHold") != null) {
                    personalRealNameInfo.setCertHoldPath(uploadResultMap.get("certHold").getRelativeFile());
                }
            }

            MemberRealNameInfo memberRealNameInfo = new MemberRealNameInfo();
            memberRealNameInfo.setId(Servlets.getLongParameter("id"));
            memberRealNameInfo.setPersonalRealNameInfo(personalRealNameInfo);

            if(personalRealNameInfo.getCertType() == CertTypeEnum.ID){
                memberRealNameService.verify(memberRealNameInfo);
            }else{
                memberRealNameService.saveVerify(memberRealNameInfo);
            }

            result.setEntity(loadEntity(request));
            result.setMessage("实名认证成功");
        } catch (Exception var5) {
            this.handleException(result, "实名认证", var5);
        }
        return result;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("serverRoot",oFileProperties.getServerRoot());
        model.put("whetherStatus", WhetherStatus.mapping());
        model.put("allChildrenCounts", MemberChildrenEnum.mapping());
        model.put("allEducationLevels", EducationLevelEnum.mapping());
        model.put("allIncomeMonths", IncomeMonthEnum.mapping());
        model.put("allHouseStatues", HouseStatueEnum.mapping());
        model.put("allCertTypes", CertTypeEnum.mapping());
        model.put("allCertStatuss", CertStatusEnum.mapping());
    }

}
