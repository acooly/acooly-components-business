package com.acooly.module.redpack.utils;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.redpack.enums.result.RedPackResultCodeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 红包算法
 * 
 * @author CuiFuQ
 *
 */
@Slf4j
public class RedPackUtils {

	public static void main(String[] args) {

		long amount = 1000;
		long num = 11;
		long total = 0;
		for (int i = 0; i < num; i++) {
			long balance = amount - total;
			long balanceNum = num - i;
			long redPack = RedPackUtils.redPack("00000", amount, balance, balanceNum);
			total = total + redPack;
		}
		System.out.println(total);
	}

	/**
	 * 
	 * @param totalAmount   总金额
	 * @param surplusAmount 剩余余额
	 * @param surplusNum    数量
	 * @return
	 */
	public static long redPack(String redPackId, long totalAmount, long surplusAmount, long surplusNum) {
		if (totalAmount <= 0) {
			throw new BusinessException("红包剩余总余额小于0", RedPackResultCodeEnum.RED_PACK_SURPLUS_LESS_0.code());
		}

		if (surplusAmount <= 0) {
			throw new BusinessException("红包剩余金额为0", RedPackResultCodeEnum.RED_PACK_SURPLUS_EQUAL_0.code());
		}

		if (surplusNum <= 0) {
			throw new BusinessException("红包剩余个数为0", RedPackResultCodeEnum.RED_PACK_NUM_EQUAL_0.code());
		}

		// 剩余金额=剩余数量
		if (surplusAmount == (surplusNum)) {
			return 1L;
		}

		// 总金额 小于 剩余数量
		if (totalAmount < surplusNum) {
			throw new BusinessException("红包总额不足,小于包括剩余个数", RedPackResultCodeEnum.RED_PACK_NOT_ENOUGH_NUM.code());
		}

		// 总金额 小于 剩余金额
		if (totalAmount < surplusAmount) {
			throw new BusinessException("红包总额小于剩余金额", RedPackResultCodeEnum.RED_PACK_NOT_ENOUGH_SURPLUS.code());
		}

		// 剩余数量小于0
		if (surplusNum <= 0) {
			throw new BusinessException("红包剩余数量小于0", RedPackResultCodeEnum.RED_PACK_NUM_LESS_0.code());
		}

		// 剩余数量 等于 1（最后一条）
		if (surplusNum == 1L) {
			return surplusAmount;
		}

		long min = 1;
		long max = (surplusAmount) / (surplusNum) * 2;
		int random = (int) ((Math.random() * max) - 1);
		long redAmount = (min + random);
		return redAmount;
	}

}
