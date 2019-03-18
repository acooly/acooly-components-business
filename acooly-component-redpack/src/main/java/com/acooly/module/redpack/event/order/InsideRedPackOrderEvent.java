package com.acooly.module.redpack.event.order;

import java.io.Serializable;

import com.acooly.module.redpack.event.dto.RedPackOrderDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsideRedPackOrderEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 红包订单dto
	 */
	private RedPackOrderDto redPackOrderDto;

	public InsideRedPackOrderEvent(RedPackOrderDto redPackOrderDto) {
		this.redPackOrderDto = redPackOrderDto;
	}

}
