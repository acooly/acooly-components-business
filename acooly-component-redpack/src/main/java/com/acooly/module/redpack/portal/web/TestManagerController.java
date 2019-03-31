/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.portal.web;

import java.util.concurrent.locks.Lock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.redpack.business.service.RedPackTradeService;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.portal.service.TestRedPackService;

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
public class TestManagerController {

	@Autowired
	private RedPackTradeService redPackTradeService;
	@Autowired
	private TestRedPackService testRedPackService;

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
			String redPackId = request.getParameter("redPackId");
			SendRedPackDto dto = new SendRedPackDto();
			dto.setRedPackId(Long.parseLong(redPackId));
			dto.setUserId(100L);
			dto.setUserName("cuifuq");

			// 数据库 锁
//			testRedPackService.sendRedPack(dto);

//			redis 分布式 锁
			redPackTradeService.sendRedPack(dto);

		} catch (BusinessException e) {
			result.setSuccess(false);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
			throw new BusinessException(e.getMessage(), e.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
		}
		long endTime = System.currentTimeMillis();
		log.info("程序运行时间：{} ms", ((endTime - startTime)));
		return result;
	}

	@RequestMapping({ "redisLock" })
	@ResponseBody
	public JsonResult redisLock(HttpServletRequest request, HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		long startTime = System.currentTimeMillis();

		Lock lock = factory.newLock("abcdefg");
		lock.lock();
		try {
			System.out.println("redisLock---------");
		} catch (Exception e) {
			throw new BusinessException("红包组件:[发送红包]发送红包失败");
		} finally {
			lock.unlock();
		}

		long endTime = System.currentTimeMillis();
		log.info("程序运行时间：{} ms", ((endTime - startTime)));
		return result;
	}

}
