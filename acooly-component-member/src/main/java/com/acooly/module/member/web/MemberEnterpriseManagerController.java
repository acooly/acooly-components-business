/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-07-31
*/
package com.acooly.module.member.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.entity.MemberEnterprise;
import com.acooly.module.member.entity.MemberProfile;
import com.acooly.module.member.enums.CertTypeEnum;
import com.acooly.module.member.enums.HoldingEnumEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.acooly.module.member.manage.MemberEnterpriseEntityService;
import com.acooly.module.member.manage.MemberEntityService;
import com.acooly.module.member.manage.MemberProfileEntityService;
import com.acooly.module.ofile.OFileProperties;

/**
 * 企业客户实名认证 管理控制器
 * 
 * @author acooly Date: 2018-07-31 20:01:45
 */
@Controller
@RequestMapping(value = "/manage/component/member/memberEnterprise")
public class MemberEnterpriseManagerController
		extends AbstractJQueryEntityController<MemberEnterprise, MemberEnterpriseEntityService> {

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private MemberEnterpriseEntityService memberEnterpriseService;

	@Autowired
	private MemberEntityService memberEntityService;

	@Autowired
	private MemberProfileEntityService memberProfileEntityService;

	@Autowired
	private OFileProperties oFileProperties;

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		Map<String, String> map = MemberUserTypeEnum.mapping();
		map.remove("personal");
		model.put("allEntTypes", map);
		
		model.put("allHoldingEnums", HoldingEnumEnum.mapping());
		model.put("allLegalCertTypes", CertTypeEnum.mapping());
		model.put("allHoldingCertTypes", CertTypeEnum.mapping());
		model.put("allWhtherStatuss", WhetherStatus.mapping());
	}

	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model,
			MemberEnterprise entity) {
		String idStr = request.getParameter("id");
		if (Strings.isNotBlank(idStr)) {
			long id = Long.parseLong(idStr);
			MemberEnterprise memberEnterprise = memberEnterpriseService.get(id);
			if (memberEnterprise == null) {
				Member member = memberEntityService.get(id);
				entity = new MemberEnterprise();
				entity.setId(id);
				entity.setUserNo(member.getUserNo());
				entity.setUsername(member.getUsername());
				memberEnterpriseService.save(entity);
			}
		}
		model.addAttribute("memberEnterprise", entity);
	}

	@Override
	protected MemberEnterprise onSave(HttpServletRequest request, HttpServletResponse response, Model model,
			MemberEnterprise entity, boolean isCreate) throws Exception {

		// 文件上传处理
		getUploadConfig().setStorageRoot(oFileProperties.getStorageRoot());
		getUploadConfig().setStorageNameSpace("enterprise");
		getUploadConfig().setUseMemery(false);
		getUploadConfig().setThumbnailEnable(true);

		String serverRoot = oFileProperties.getServerRoot();

		Map<String, UploadResult> uploadResultMap = doUpload(request);
		if (uploadResultMap != null) {

			// 营业执照图片地址
			UploadResult licencePathFile = uploadResultMap.get("licencePathFile");
			if (licencePathFile != null) {
				entity.setLicencePath(serverRoot + licencePathFile.getRelativeFile());
			}

			// 法人证件正面图片
			UploadResult legalCertFrontPathFile = uploadResultMap.get("legalCertFrontPathFile");
			if (legalCertFrontPathFile != null) {
				entity.setLegalCertFrontPath(serverRoot + legalCertFrontPathFile.getRelativeFile());
			}

			// 法人证件背面图片
			UploadResult legalCertBackPathFile = uploadResultMap.get("legalCertBackPathFile");
			if (legalCertBackPathFile != null) {
				entity.setLegalCertBackPath(serverRoot + legalCertBackPathFile.getRelativeFile());
			}

			// 股东或实际控制人证件正面图片
			UploadResult holdingCertFrontPathFile = uploadResultMap.get("holdingCertFrontPathFile");
			if (holdingCertFrontPathFile != null) {
				entity.setHoldingCertFrontPath(serverRoot + holdingCertFrontPathFile.getRelativeFile());
			}

			// 股东或实际控制人证件背面图片
			UploadResult holdingCertBackPathFile = uploadResultMap.get("holdingCertBackPathFile");
			if (holdingCertBackPathFile != null) {
				entity.setHoldingCertBackPath(serverRoot + holdingCertBackPathFile.getRelativeFile());
			}

			// 股东或实际控制人证件背面图片
			UploadResult organizationPathFile = uploadResultMap.get("organizationPathFile");
			if (organizationPathFile != null) {
				entity.setOrganizationPath(serverRoot + organizationPathFile.getRelativeFile());
			}

			// 开户许可证图片
			UploadResult accountLicensePathFile = uploadResultMap.get("accountLicensePathFile");
			if (accountLicensePathFile != null) {
				entity.setAccountLicensePath(serverRoot + accountLicensePathFile.getRelativeFile());
			}

			// 税务登记证图片
			UploadResult taxAuthorityPathFile = uploadResultMap.get("taxAuthorityPathFile");
			if (taxAuthorityPathFile != null) {
				entity.setTaxAuthorityPath(serverRoot + taxAuthorityPathFile.getRelativeFile());
			}
		}

		// 实名认证状态
		String enterpriseStatus = request.getParameter("enterpriseStatus");
		WhetherStatus status = WhetherStatus.findStatus(enterpriseStatus);

		// 会员信息
		Member memberEntity = memberEntityService.findUniqueByUserNo(entity.getUserNo());
		memberEntity.setRealName(entity.getEntName());
		memberEntityService.update(memberEntity);

		// 会员扩展信息
		MemberProfile memberProfile = memberProfileEntityService.get(memberEntity.getId());
		memberProfile.setRealNameStatus(status);
		memberProfileEntityService.update(memberProfile);

		entity.setCertStatus(status);

		return entity;
	}

}
