/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by cuifuq
* date:2019-03-05
*/
package com.acooly.module.redpack.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.module.redpack.business.service.RedPackTradeService;
import com.acooly.module.redpack.dto.CreateRedPackDto;
import com.acooly.module.redpack.dto.SendRedPackDto;
import com.acooly.module.redpack.event.dto.RedPackDto;

/**
 * 红包 管理控制器
 * 
 * @author cuifuq Date: 2019-03-05 18:18:08
 */
@Controller
@RequestMapping(value = "/test/redPack")
public class TestManagerController {

	@Autowired
	private RedPackTradeService redPackTradeService;

	public class MyThread extends Thread {
		public void run() {
			int size = 2;
			long totalAmount = 10000L;
			long totalNum = 100;
			long startTime = System.currentTimeMillis();

			for (int i = 1; i < (size + 1); i++) {
				// 创建红包
				CreateRedPackDto createDto = new CreateRedPackDto();
				createDto.setSendUserId(100L);
				createDto.setSendUserName("cuifuq0");
				createDto.setTitle("红包00");
				createDto.setPartakeNum(1000L);
				createDto.setTotalAmount(totalAmount);

				createDto.setTotalNum(totalNum);
				createDto.setRemark("开心工作");
				createDto.setBusinessId("10000");
				
				RedPackDto eventDto = redPackTradeService.createRedPack(createDto);

				while (true) {
					RedPackDto redPackEvent = redPackTradeService.findRedPack(eventDto.getRedPackId());
					if (redPackEvent.getTotalAmount() > redPackEvent.getSendOutAmount()) {

						// 发送红包
						SendRedPackDto dto = new SendRedPackDto();
						dto.setRedPackId(eventDto.getRedPackId());
						dto.setUserId(100L);
						dto.setUserName("cuifuq" + i);
						redPackTradeService.sendRedPack(dto);
					} else {
						break;
					}
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
		}
	}

	@RequestMapping({ "index2" })
	@ResponseBody
	public void index2(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Thread myThread1 = new MyThread();
			myThread1.start();
			
			
			Thread myThread2 = new MyThread();
//			myThread2.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "index" })
	@ResponseBody
	public void index(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			int size = 10;

			long totalAmount = 1000L;
			long maxNum = 100;

			for (int i = 1; i < (size + 1); i++) {
				long startTime = System.currentTimeMillis();

				if (size != i) {

					// 创建红包
					CreateRedPackDto createDto = new CreateRedPackDto();
					createDto.setSendUserId(100L);
					createDto.setSendUserName("cuifuq0");
					createDto.setTitle("红包00");
					createDto.setPartakeNum(1000L);
					createDto.setTotalAmount(totalAmount);

					int totalNum = (1 + (int) (Math.random() * maxNum));
					createDto.setTotalNum((long) totalNum);
					createDto.setRemark("开心工作");
					createDto.setBusinessId("10000");
					RedPackDto eventDto = redPackTradeService.createRedPack(createDto);

					while (true) {
						RedPackDto redPackEvent = redPackTradeService.findRedPack(eventDto.getRedPackId());
						if (redPackEvent.getTotalAmount() > redPackEvent.getSendOutAmount()) {

							// 发送红包
							SendRedPackDto dto = new SendRedPackDto();
							dto.setRedPackId(eventDto.getRedPackId());
							dto.setUserId(100L);
							dto.setUserName("cuifuq" + i);
							redPackTradeService.sendRedPack(dto);
						} else {
							break;
						}
					}
				} else {
					// 创建红包
					CreateRedPackDto createDto = new CreateRedPackDto();
					createDto.setSendUserId(100L);
					createDto.setSendUserName("cuifuq0");
					createDto.setTitle("红包00");
					createDto.setPartakeNum(1000L);
					createDto.setTotalAmount(totalAmount);

					int totalNum = (1 + (int) (Math.random() * maxNum));
					createDto.setTotalNum((long) totalNum);
					createDto.setRemark("开心工作");
					createDto.setBusinessId("10000");
					RedPackDto eventDto = redPackTradeService.createRedPack(createDto);

					while (true) {
						try {

							RedPackDto redPackEvent = redPackTradeService.findRedPack(eventDto.getRedPackId());
							if ((redPackEvent.getTotalNum() - redPackEvent.getSendOutNum()) == 1) {
								try {
									redPackTradeService.refundRedPack(eventDto.getRedPackId());
								} catch (Exception e) {
									break;
								}
							} else if ((redPackEvent.getTotalNum() - redPackEvent.getSendOutNum()) == 0) {
								break;
							} else {
								// 发送红包
								SendRedPackDto dto = new SendRedPackDto();
								dto.setRedPackId(eventDto.getRedPackId());
								dto.setUserId(100L);
								dto.setUserName("cuifuq" + i);
								redPackTradeService.sendRedPack(dto);
							}
						} catch (Exception e) {
							break;
						}
					}
				}
				long endTime = System.currentTimeMillis();
				System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
