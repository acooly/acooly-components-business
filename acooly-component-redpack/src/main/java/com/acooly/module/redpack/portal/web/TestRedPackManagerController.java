/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.portal.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.redpack.business.service.RedPackTradeService;
import com.acooly.module.redpack.dto.CreateRedPackDto;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 红包 管理控制器
 * 
 * @author cuifuq Date: 2019-03-05 18:18:08
 */
@Profile("!online")
@Slf4j
@Controller
@RequestMapping(value = "/test/redPack")
public class TestRedPackManagerController {

	@Autowired
	private RedPackTradeService redPackTradeService;
	@Autowired
	private DistributedLockFactory factory;

	@Autowired
	private RedisTemplate redisTemplate;

	@RequestMapping({ "index" })
	@ResponseBody
	public JsonResult index(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		long startTime = System.currentTimeMillis();

		try {

			CreateRedPackDto dto = new CreateRedPackDto();
			dto.setTitle("12321");
			dto.setSendUserId(100L);
			dto.setSendUserName("111");
			dto.setTotalAmount(100L);
			dto.setTotalNum(100L);
			dto.setPartakeNum(10L);
			dto.setPartakeNum(0L);
			dto.setBusinessId("1");

			dto.setOverdueTime(Dates.addDate(new Date(), 10L,TimeUnit.MINUTES));

			System.out.println(dto.getOverdueTime());
			redPackTradeService.createRedPack(dto);

//			Long redPackId=536L;
//			redPackTradeService.findRedPack(redPackId);
//			List<RedPackOrderDto> list = redPackTradeService.findRedPackOrder(redPackId);
//			

			String redPackId = request.getParameter("redPackId");
			redPackId = "28";
			SendRedPackDto sdto = new SendRedPackDto();
			sdto.setRedPackId(Long.parseLong(redPackId));
			sdto.setUserId(100L);
			sdto.setUserName("cuifuq");
			
			Map<String, Object> dataMap = Maps.newHashMap();
			dataMap.put("head_img",
					"https://wx.qlogo.cn/mmopen/vi_32/Kicpz5MhgFzUUxMNZLFW2mibEs8Ok1usnjRn3QIQJkicZn1KicOtI9PJY1gibNQdg3m5RYHB4CLMT48rOunQK8nH4icg/132");
			dataMap.put("head_img2", "?一休哥😝");
			dataMap.put("bbbb", "bbb");
			dataMap.put("ccc", "ccc");
			sdto.setDataMap(dataMap);
			
			
			redPackTradeService.sendRedPack(sdto);

			List<RedPackOrderDto> lists = redPackTradeService.findRedPackOrderSort(Long.parseLong(redPackId), true);
			for (RedPackOrderDto redPackOrderDto : lists) {
//				System.out.println("---2---" + redPackOrderDto.getUserId() + "----" + redPackOrderDto.getCreateTime()
//						+ "-------" + redPackOrderDto.getAmount());
			}
//
			Map<Object, Object> data = Maps.newHashMap();
			data.put("key", lists);
			result.setData(data);

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

	@RequestMapping({ "redisLock" })
	@ResponseBody
	public JsonResult redisLock(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		long startTime = System.currentTimeMillis();

		Lock lock = factory.newLock("abcdefg");
//		lock.lock();
		try {
			if (lock.tryLock(1, TimeUnit.SECONDS)) {

				System.out.println("redisLock---------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("红包组件:[发送红包]发送红包失败");
		} finally {
			lock.unlock();
		}

		long endTime = System.currentTimeMillis();
		log.info("程序运行时间：{} ms", ((endTime - startTime)));
		return result;
	}

}
