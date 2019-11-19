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
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.acooly.module.countnum.dto.CountNumGameOrderRankDto;
import com.acooly.module.countnum.dto.order.CountNumGameResultDto;
import com.acooly.module.countnum.dto.order.CreateCountNumGameDto;
import com.acooly.module.countnum.enums.CountNumTypeEnum;
import com.acooly.module.distributedlock.redis.RedisLockEntityHolder;

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
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

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
			dto.setType(CountNumTypeEnum.NUM_DESC_TIME_ASC);

			dto.setBusinessId("100000L");

			Date currentDate = new Date();
			Date overdueTime = Dates.addDate(currentDate, 1000000000L);
			dto.setOverdueTime(overdueTime);
			dto.setLimitNum(5L);

//			CountNumGameDto countDto = countNumGameService.createCountNumGame(dto);

//			Long countNumId = countDto.getCountNumId();
			Long countNumId = 4860L;

			CountNumGameResultDto gameDto = new CountNumGameResultDto();
			gameDto.setCountNumId(countNumId);
//			gameDto.setUserId(119L);
			gameDto.setUserId((long) (1 + Math.random() * (50 - 10 + 1)));

			gameDto.setUserName("888OOO");
			gameDto.setNum((long) (0 + Math.random() * (99 - 10 + 1)));
			gameDto.setTime((long) (0 + Math.random() * (99 - 10 + 1)));
			
			gameDto.setNum(0L);
			gameDto.setTime(0L);
			
//			gameDto.setNum(0L);

			Map<String, Object> dataMap = Maps.newHashMap();
			dataMap.put("head_img",
					"https://wx.qlogo.cn/mmopen/vi_32/Kicpz5MhgFzUUxMNZLFW2mibEs8Ok1usnjRn3QIQJkicZn1KicOtI9PJY1gibNQdg3m5RYHB4CLMT48rOunQK8nH4icg/132");
			dataMap.put("head_img2", "?一休哥😝");
			dataMap.put("bbbb", "bbb");
			dataMap.put("ccc", "ccc");
			gameDto.setDataMap(dataMap);

			CountNumGameOrderDto orderDto = countNumGameService.submitCountNumGameResult(gameDto);

			// 提交游戏

//			countNumGameService.findCountNum(countNumId);
			List<CountNumGameOrderDto> orderDtoList = countNumGameService.findCountNumGameOrder(countNumId);
			Map<Object, Object> data = Maps.newHashMap();
			data.put("key", orderDtoList);
			result.setData(data);

			System.out.println(orderDtoList.size());
			for (CountNumGameOrderDto d : orderDtoList) {
				Map<String, Object> dataMaps = d.getDataMap();
//				System.out.println(dataMaps.get("head_img"));
				System.out.println("成绩：" + d.getNum() + "---" + d.getTime() + "----时间：" + Dates.format(d.getValidTime())
						+ "----用户" + d.getUserId() + "----参与次数:" + d.getJoinNum()+"----id:"+d.getOrderNo());
			}

//			redisTemplate.opsForValue().set(orderDto.getOrderNo(), dto, 10, TimeUnit.SECONDS);

			CountNumGameOrderRankDto countNumGame = countNumGameService.userRanking(gameDto.getUserId(), countNumId,
					true);
			System.out.println("排名：" + countNumGame.getRank() + "----击败对手：" + countNumGame.getOverstepRate()
					+ "%----成绩：" + countNumGame.getNum() + "----用户id:" + countNumGame.getUserId() + "----参与次数:"
					+ countNumGame.getJoinNum());
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

	public static void main(String[] args) {

	}

}
