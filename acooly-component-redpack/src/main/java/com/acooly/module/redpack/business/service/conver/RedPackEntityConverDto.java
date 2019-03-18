package com.acooly.module.redpack.business.service.conver;

import java.util.List;

import com.acooly.module.redpack.entity.RedPack;
import com.acooly.module.redpack.entity.RedPackOrder;
import com.acooly.module.redpack.event.dto.RedPackDto;
import com.acooly.module.redpack.event.dto.RedPackOrderDto;

public class RedPackEntityConverDto {

	/**
	 * 红包实体转化为dto
	 * 
	 * @param redPack
	 * @param dto
	 * @return
	 */
	public static RedPackDto converRedPackDto(RedPack redPack, RedPackDto dto) {
		dto.setRedPackId(redPack.getId());
		dto.setTitle(redPack.getTitle());
		dto.setSendUserId(redPack.getSendUserId());
		dto.setSendUserName(redPack.getSendUserName());
		dto.setOverdueTime(redPack.getOverdueTime());
		dto.setTotalAmount(redPack.getTotalAmount());
		dto.setTotalNum(redPack.getTotalNum());
		dto.setSendOutAmount(redPack.getSendOutAmount());
		dto.setSendOutNum(redPack.getSendOutNum());
		dto.setPartakeNum(redPack.getPartakeNum());
		dto.setBusinessId(redPack.getBusinessId());
		return dto;
	}

	/**
	 * 红包订单转化为dto
	 * 
	 * @param redPackOrderList
	 * @param eventDtoList
	 * @param eventDto
	 * @return
	 */
	public static List<RedPackOrderDto> converSendRedPackDto(List<RedPackOrder> redPackOrderList,
			List<RedPackOrderDto> orderDtoList) {
		for (RedPackOrder redPackOrder : redPackOrderList) {
			RedPackOrderDto orderDto = new RedPackOrderDto();
			orderDto.setRedPackId(redPackOrder.getRedPackId());
			orderDto.setUserId(redPackOrder.getUserId());
			orderDto.setUserName(redPackOrder.getUserName());
			orderDto.setAmount(redPackOrder.getAmount());
			orderDto.setIsFirst(redPackOrder.getIsFirst());
			orderDto.setType(redPackOrder.getType());
			orderDtoList.add(orderDto);
		}
		return orderDtoList;
	}

}
