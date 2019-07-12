/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.countnum.portal.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Dates;
import com.acooly.module.countnum.business.service.CountNumGameService;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.dto.order.CreateCountNumGameDto;
import com.acooly.module.countnum.enums.CountNumTypeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 红包 管理控制器
 * 
 * @author cuifuq Date: 2019-03-05 18:18:08
 */
@Profile("!online")
@Slf4j
@Controller
@RequestMapping(value = "/test/countNum/")
public class TestCountNumManagerController {

	@Autowired
	private CountNumGameService countNumGameService;

	@RequestMapping({ "index" })
	@ResponseBody
	public JsonResult index(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		long startTime = System.currentTimeMillis();

		try {

			// 创建游戏
			CreateCountNumGameDto dto = new CreateCountNumGameDto();
			dto.setTitle("计数游戏01");
			dto.setCreateUserId(999L);
			dto.setCreateUserName("999OOO");
			dto.setType(CountNumTypeEnum.NUM_LIMIT);
			dto.setBusinessId("100000L");
//			CountNumGameDto countDto = countNumGameService.createCountNumGame(dto);

//			Long countNumId = countDto.getCountNumId();
			Long countNumId = 16L;

			CountNumGameResultDto gameDto = new CountNumGameResultDto();
			gameDto.setCountNumId(countNumId);
			gameDto.setUserId(888L);
			gameDto.setUserName("888OOO");
			gameDto.setNum((long) (1 + Math.random() * (99 - 10 + 1)));
			countNumGameService.submitCountNumGameResult(gameDto);

			// 提交游戏

			countNumGameService.findCountNum(countNumId);
			List<CountNumGameOrderDto> list = countNumGameService.findCountNumGameOrder(countNumId);
			Map<Object, Object> data = Maps.newHashMap();
			data.put("key", list);
			result.setData(data);

//			countNumGameService.userRanking(8888L, countNumId, true);

//			Date overdueDate = Dates.parse("2019-07-08 11:06:00");
//			countNumGameService.countNumGameOverdueFinish(countNumId, overdueDate);

		} catch (BusinessException e) {
			result.setSuccess(false);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
			throw new BusinessException(e.getMessage(), e.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("程序运行时间：{} s", ((endTime - startTime) / 1000.00));
		return result;
	}

}
