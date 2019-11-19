package com.acooly.module.countnum.business.service.conver;

import java.util.List;
import java.util.Map;

import com.acooly.core.utils.Strings;
import com.acooly.module.countnum.dto.CountNumGameDto;
import com.acooly.module.countnum.dto.CountNumGameOrderDto;
import com.acooly.module.countnum.entity.CountNum;
import com.acooly.module.countnum.entity.CountNumOrder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class CountNumEntityConverDto {

	/**
	 * 计数游戏-实体转化为dto
	 * 
	 * @param countNum
	 * @param dto
	 * @return
	 */
	public static CountNumGameDto converCountNumDto(CountNum countNum, CountNumGameDto dto) {
		dto.setCountNumId(countNum.getId());
		dto.setTitle(countNum.getTitle());
		dto.setCreateUserId(countNum.getCreateUserId());
		dto.setCreateUserName(countNum.getCreateUserName());
		dto.setType(countNum.getType());
		dto.setOverdueTime(countNum.getOverdueTime());
		dto.setMaxNum(countNum.getMaxNum());
		dto.setLimitNum(countNum.getLimitNum());
		dto.setStatus(countNum.getStatus());
		dto.setBusinessId(countNum.getBusinessId());
		return dto;
	}

	/**
	 * 计数游戏记录-订单转化为dto
	 * 
	 * @param countNumOrderList
	 * @param eventDtoList
	 * @param eventDto
	 * @return
	 */
	public static List<CountNumGameOrderDto> converCountNumOrderDtoList(List<CountNumOrder> countNumOrderList,
			List<CountNumGameOrderDto> orderDtoList) {
		for (CountNumOrder countNumOrder : countNumOrderList) {
			CountNumGameOrderDto orderDto = new CountNumGameOrderDto();
			orderDto.setCountNumId(countNumOrder.getCountId());
			orderDto.setCountNumOrderId(countNumOrder.getId());
			orderDto.setTitle(countNumOrder.getCountTitle());
			orderDto.setOrderNo(countNumOrder.getOrderNo());
			orderDto.setUserId(countNumOrder.getUserId());
			orderDto.setUserName(countNumOrder.getUserName());
			orderDto.setNum(countNumOrder.getNum());
			orderDto.setCreateTime(countNumOrder.getCreateTime());
			orderDtoList.add(orderDto);
		}
		return orderDtoList;
	}

	/**
	 * 计数游戏记录-订单转化为dto
	 * 
	 * @param countNumOrderList
	 * @param eventDtoList
	 * @param eventDto
	 * @return
	 */
	public static CountNumGameOrderDto converCountNumOrderDto(CountNumOrder countNumOrder,
			CountNumGameOrderDto orderDto) {
		orderDto.setCountNumId(countNumOrder.getCountId());
		orderDto.setCountNumOrderId(countNumOrder.getId());
		orderDto.setTitle(countNumOrder.getCountTitle());
		orderDto.setOrderNo(countNumOrder.getOrderNo());
		orderDto.setUserId(countNumOrder.getUserId());
		orderDto.setUserName(countNumOrder.getUserName());
		orderDto.setNum(countNumOrder.getNum());
		orderDto.setTime(countNumOrder.getTime());
		orderDto.setCreateTime(countNumOrder.getCreateTime());
		orderDto.setJoinNum(countNumOrder.getJoinNum());
		orderDto.setValidTime(countNumOrder.getValidTime());

		String dataMapStr = countNumOrder.getDataMap();
		if (Strings.isNotBlank(dataMapStr)) {
			Map<String, Object> dataMap = JSON.parseObject(dataMapStr);
			orderDto.setDataMap(dataMap);
		}
		return orderDto;
	}

}
