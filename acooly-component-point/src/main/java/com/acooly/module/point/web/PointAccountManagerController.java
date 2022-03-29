/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.enums.PointAccountStatus;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointTradeService;
import com.google.common.collect.Lists;

/**
 * 积分账户 管理控制器
 *
 * @author cuifuqiang Date: 2017-02-03 22:45:13
 */
@Controller
@RequestMapping(value = "/manage/point/pointAccount")
public class PointAccountManagerController extends AbstractPointManageController<PointAccount, PointAccountService> {

	@SuppressWarnings("unused")
	@Autowired
	private PointAccountService pointAccountService;
	@Autowired
	private PointTradeService pointTradeService;

	{
		allowMapping = "*";
	}

	@RequestMapping(value = "updateStatusJson")
	@ResponseBody
	@Transactional
	public JsonEntityResult<PointAccount> updateStatusJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<PointAccount> result = new JsonEntityResult<PointAccount>();
		try {
			String userNo = request.getParameter("userNo");
			String status = request.getParameter("status");
			PointAccount pointAccount = pointAccountService.lockByUserNo(userNo);
			pointAccount.setStatus(PointAccountStatus.find(status));
			pointAccountService.update(pointAccount);
			result.setEntity(pointAccount);
            result.setMessage("更新状态成功");
		} catch (Exception e) {
			handleException(result, "积分账户更新状态", e);
		}
		return result;
	}

	@RequestMapping(value = { "importJson" })
	@ResponseBody
	public JsonResult importJson(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		allow(request, response, MappingMethod.imports);
		try {
			List<PointAccount> entities = doImport(request, response);
			result.setMessage("积分发放成功，成功发放用户数：" + entities.size() + ",关闭页面,刷新查看发放结果");
		} catch (Exception e) {
			handleException(result, "Excel导入", e);
		}
		return result;
	}

	protected List<PointAccount> doImport(HttpServletRequest request, HttpServletResponse response, FileType fileType)
			throws Exception {
		List<PointAccount> lists = Lists.newArrayList();
		Map<String, UploadResult> uploadResults = doUpload(request);
		long rowNum = 1L;
		List<List<String>> lineLists = loadImportFile(uploadResults, fileType, "UTF-8");
		for (List<String> lines : lineLists) {
			if (rowNum == 1L) {
				rowNum = rowNum + 1;
				continue;
			}
			String userNo = lines.get(0);
			String userName = lines.get(1);
			String point = lines.get(2);
			String busiType = lines.get(3);
			String busiTypeText = lines.get(4);
			String overdueDate = lines.get(5);
			String comments = lines.get(6);
			PointTradeInfoDto pointTradeDto = new PointTradeInfoDto();
			pointTradeDto.setRepeatTrade(true);
			pointTradeDto.setBusiType(busiType);
			pointTradeDto.setBusiTypeText(busiTypeText);
			pointTradeDto.setComments(comments);
			pointTradeDto.setBusiData(getBossUser().getUsername() + ":批量发放");
			pointTradeService.pointProduce(userNo, userName, Long.parseLong(point), overdueDate, pointTradeDto);
			lists.add(pointAccountService.findByUserNo(userNo));
		}
		return lists;
	}

	@RequestMapping(value = "grantJson")
	@ResponseBody
	public JsonEntityResult<PointAccount> grantJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<PointAccount> result = new JsonEntityResult<PointAccount>();
		allow(request, response, MappingMethod.create);
		try {
			String userNos = request.getParameter("userNos");
			String point = request.getParameter("point");
			String busiType = request.getParameter("busiType");
			String busiTypeText = getBusiTypeText(busiType);
			String overdueDate = request.getParameter("overdueDate");

			String comments = request.getParameter("comments");
			List<String> userNoList = Lists.newArrayList(StringUtils.split(userNos, ","));
			if (Collections3.isEmpty(userNoList)) {
				throw new RuntimeException("用户名不能为空，多个用户名使用英文逗号分隔");
			}

			if (userNoList.size() > 1000) {
				throw new RuntimeException("最多支持1000个用户名");
			}

			for (String userNo : userNoList) {
				PointTradeInfoDto pointTradeDto = new PointTradeInfoDto();
				pointTradeDto.setRepeatTrade(true);
				pointTradeDto.setBusiType(busiType);
				pointTradeDto.setBusiTypeText(busiTypeText);
				pointTradeDto.setBusiData(getBossUser().getUsername() + ":发放");
				pointTradeDto.setComments(comments);
				pointTradeService.pointProduce(userNo, null, Long.parseLong(point), overdueDate, pointTradeDto);
			}
			result.setMessage("积分发放成功");
		} catch (Exception e) {
			handleException(result, "积分发放成功", e);
		}
		return result;
	}

	/**
	 * 积分发放页面
	 *
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "grant")
	public String grant(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
		} catch (Exception e) {
			handleException("积分发放", e, request);
		}
		return getListView() + "Grant";
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		super.referenceData(request, model);
		model.put("allStatuss", PointAccountStatus.mapping());
		model.put("allPointGrades", getPointGradeMap());
		model.put("allBusiTypeEnumss", getBusiTypeMap());
	}

}
