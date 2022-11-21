/*
 * acooly.cn Inc.
 * Copyright (c) 2021 All Rights Reserved.
 * create by zhangpu@acooly.cn
 * date:2021-08-12
 */
package cn.acooly.component.assetmgmt.web;

import cn.acooly.component.assetmgmt.entity.Secretbox;
import cn.acooly.component.assetmgmt.enums.SecretStatus;
import cn.acooly.component.assetmgmt.enums.SecretTypeEnum;
import cn.acooly.component.assetmgmt.enums.ServiceTypeEnum;
import cn.acooly.component.assetmgmt.service.SecretboxService;
import cn.acooly.component.assetmgmt.utils.ImportDatas;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.defence.password.PasswordStrength;
import com.acooly.module.defence.password.Passwords;
import com.acooly.module.treetype.entity.TreeType;
import com.acooly.module.treetype.service.TreeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 资产管理 管理控制器
 *
 * @author zhangpu@acooly.cn
 * @date 2021-08-12 22:44:09
 */
@Controller
@RequestMapping(value = "/manage/assetmgmt/secretbox")
public class SecretboxManagerController extends AbstractJsonEntityController<Secretbox, SecretboxService> {

    @Autowired
    private SecretboxService secretboxService;
    @Autowired
    private TreeTypeService treeTypeService;

    {
        allowMapping = "*";
    }

    @RequestMapping("passwordGenerate")
    @ResponseBody
    public JsonResult passwordGenerate(PasswordStrength passwordStrength, Integer length) {
        JsonResult result = new JsonResult();
        if (passwordStrength == null) {
            passwordStrength = PasswordStrength.complex;
        }
        if (length == null) {
            length = 12;
        }
        result.appendData("password", Passwords.generate(passwordStrength, length));
        return result;
    }


    @Override
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
        String typeCode = Servlets.getParameter(request, "typeCode");
        doBindTypeToModel(typeCode, model);
    }

    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, Secretbox entity) {
        String typeCode = entity.getTypeCode();
        doBindTypeToModel(typeCode, model);
    }

    /**
     * 导入视图
     */
    @Override
    public String importView(Model model, HttpServletRequest request, HttpServletResponse response) {
        String typeCode = Servlets.getParameter(request, "typeCode");
        doBindTypeToModel(typeCode, model);
        return super.importView(model, request, response);
    }

    /**
     * 导入数据的单行转换
     *
     * @param fields
     * @param request
     * @return
     */
    @Override
    protected Secretbox doImportEntity(List<String> fields, HttpServletRequest request) {
        String typeCode = Servlets.getParameter(request, "typeCode");
        Secretbox secretbox = new Secretbox();
        secretbox.setCode(ImportDatas.toSafe(fields, 0));
        secretbox.setTitle(ImportDatas.toSafe(fields, 1));
        secretbox.setTypeCode(typeCode);
        secretbox.setSecretType(SecretTypeEnum.find(fields.get(2)));
        secretbox.setUsername(ImportDatas.toSafe(fields, 3));
        secretbox.setPassword(ImportDatas.toSafe(fields, 4));
        secretbox.setAccessPoint(ImportDatas.toSafe(fields, 5));
        secretbox.setServiceType(ServiceTypeEnum.find(fields.get(6)));
        secretbox.setSubject(ImportDatas.toSafe(fields, 7));
        secretbox.setBindEmail(ImportDatas.toSafe(fields, 8));
        secretbox.setBindMobileNo(ImportDatas.toSafe(fields, 9));
        secretbox.setSecretStatus(SecretStatus.enable);
        secretbox.setComments(ImportDatas.toSafe(fields, 10));
        return secretbox;
    }

    protected void doBindTypeToModel(String typeCode, Model model) {
        if (Strings.isBlank(typeCode)) {
            return;
        }
        model.addAttribute("typeCode", typeCode);
        TreeType treeType = treeTypeService.findByCode(Secretbox.TREE_TYPE_THEME, typeCode);
        if (treeType != null) {
            model.addAttribute("typeName", treeType.getName());
        }
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allSecretTypes", SecretTypeEnum.mapping());
        model.put("allServiceTypes", ServiceTypeEnum.mapping());
        model.put("allPasswordStrengths", PasswordStrength.mapping());
    }


}
