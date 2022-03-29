/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.point.PointProperties;
import com.acooly.module.point.dao.PointAccountDao;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointGrade;
import com.acooly.module.point.enums.PointAccountStatus;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointGradeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 积分账户 Service实现
 *
 * <p>
 * Date: 2017-02-03 22:45:13
 *
 * @author cuifuqiang
 */
@Slf4j
@Service("pointAccountService")
public class PointAccountServiceImpl extends EntityServiceImpl<PointAccount, PointAccountDao>
		implements PointAccountService {

	@Autowired
	private PointGradeService pointGradeService;
	@Autowired
	protected PointProperties pointProperties;

	@Override
	@Transactional
	public PointAccount syncPointAccount(String userNo, String userName) {
		if (!pointProperties.isEnable()) {
			throw new BusinessException("积分组件未开启，请开启组件[acooly.point.enable=true]");
		}
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			pointAccount = new PointAccount();
			pointAccount.setUserNo(userNo);
			pointAccount.setUserName(userName);
			PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
			pointAccount.setGradeId(pointGrade.getId());
			getEntityDao().create(pointAccount);
		} else {
			pointAccount.setUserName(userName);
			PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
			pointAccount.setGradeId(pointGrade.getId());
			getEntityDao().update(pointAccount);
		}
		return pointAccount;
	}

	@Override
	public PointAccount findByUserNo(String userNo) {
		PointAccount pointAccount = getEntityDao().findByUserNo(userNo);
		long gradeId = pointAccount.getGradeId();
		PointGrade pointGrade = pointGradeService.get(gradeId);
		pointAccount.setGradePicture(pointGrade.getPicture());
		// 积分账户小于0；统一设置为0L
		if (pointAccount.getDebtPoint() > 0) {
			pointAccount.setAvailable(pointAccount.getAvailable() - pointAccount.getDebtPoint());
		}
		return pointAccount;
	}

	@Override
	@Transactional
	public PointAccount pointProduce(String userNo, String userName, long point) {
		if (!pointProperties.isEnable()) {
			throw new BusinessException("积分组件未开启，请开启组件[acooly.point.enable=true]");
		}
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			pointAccount = new PointAccount();
			pointAccount.setUserNo(userNo);
			pointAccount.setUserName(userName);
			pointAccount.setBalance(pointAccount.getBalance() + point);
			pointAccount.setTotalProducePoint(pointAccount.getTotalProducePoint() + point);
			PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
			pointAccount.setGradeId(pointGrade.getId());
			getEntityDao().create(pointAccount);
		} else {
			if (pointAccount.getAvailable() < 0) {
				throw new BusinessException("积分账户异常,请联系管理员");
			}
			if (Strings.isNotBlank(userName)) {
				pointAccount.setUserName(userName);
			}
			pointAccount.setBalance(pointAccount.getBalance() + point);
			pointAccount.setTotalProducePoint(pointAccount.getTotalProducePoint() + point);

			// 负债账户
			long debtPointDB = pointAccount.getDebtPoint();
			if (debtPointDB > 0) {
				// 当前有效积分A
				long availablePoint = pointAccount.getBalance() - pointAccount.getFreeze();
				long diffPoint = availablePoint - debtPointDB;

				log.info("积分组件-负债账户-交易前,userNo{},userName:{},当前有效积分:{},当前负债积分:{},交易积分:{}", userNo, userName,
						availablePoint, debtPointDB, point);
				if (diffPoint >= 0) {
					pointAccount.setDebtPoint(0L);
					pointAccount.setBalance(pointAccount.getBalance() - debtPointDB);
				} else {
					pointAccount.setDebtPoint(debtPointDB - availablePoint);
					pointAccount.setBalance(pointAccount.getFreeze());
				}
			}

			PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
			pointAccount.setGradeId(pointGrade.getId());
			getEntityDao().update(pointAccount);
		}
		return pointAccount;
	}

	@Override
	@Transactional
	public PointAccount pointExpense(String userNo, String userName, long point, boolean isFreeze) {
		checkPointValue(userNo, point);
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			throw new BusinessException(userName + "积分账户不存在,无法完成积分消费");
		}

		if (pointAccount.getStatus() == PointAccountStatus.invalid) {
			throw new BusinessException(userName + "积分账户状态无效,请联系管理员");
		}

		// 冻结模式
		if (isFreeze) {
			if (pointAccount.getFreeze() < point) {
				throw new BusinessException(userNo + "冻结积分不足,无法完成积分消费");
			}
			pointAccount.setFreeze(pointAccount.getFreeze() - point);
		}

		pointAccount.setUserName(userName);
		pointAccount.setBalance(pointAccount.getBalance() - point);
		pointAccount.setTotalExpensePoint(pointAccount.getTotalExpensePoint() + point);

		if (pointAccount.getBalance() - pointAccount.getFreeze() < 0) {
			throw new BusinessException(userNo + ":积分余额不足,无法完成积分消费");
		}

		PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
		pointAccount.setGradeId(pointGrade.getId());

		getEntityDao().update(pointAccount);
		return pointAccount;
	}

	@Override
	public PointAccount pointExpenseByCancel(String userNo, String userName, long point) {
		checkPointValue(userNo, point);
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			throw new BusinessException(userName + "积分账户不存在,无法完成积分取消");
		}

		// 交易前-积分账户余额
		long pointBalanceDB = pointAccount.getBalance();
		// 消费后的账户余额
		long pointBalance = pointBalanceDB - point;
		// 负债账户判断逻辑
		if ((pointProperties.isSetDebt()) && (pointBalance < 0)) {
			// 设置负债账户，有效积分不能小于0
			pointAccount.setBalance(0L);
			// 取消积分 大于 账户余额
			pointAccount.setDebtPoint(pointAccount.getDebtPoint() + (point - pointBalanceDB));
			// 总消费积分
			pointAccount.setTotalExpensePoint(pointAccount.getTotalExpensePoint() + point);
			log.info("积分组件-负债账户,userNo{},userName:{},消费后负债积分:{}", userNo, userName, pointAccount.getDebtPoint());
		} else {
			// 不设置负债账户，有效积分必须大于0
			pointAccount.setBalance(pointBalance);
			pointAccount.setTotalExpensePoint(pointAccount.getTotalExpensePoint() + point);
		}

		if (pointAccount.getBalance() - pointAccount.getFreeze() < 0) {
			throw new BusinessException(userNo + ":积分余额不足,无法取消,根据业务场景请开通负债账户");
		}

		PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
		pointAccount.setGradeId(pointGrade.getId());

		getEntityDao().update(pointAccount);
		return pointAccount;
	}

	@Transactional
	public PointAccount pointOverdueClear(String userNo, String userName, long totalClearPoint) {
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			throw new BusinessException(userName + "积分账户不存在,无法完成积分清零");
		}
		pointAccount.setUserName(userName);
		pointAccount.setCountOverduePoint(pointAccount.getCountOverduePoint() + totalClearPoint);

		// 账户余额
		long available = pointAccount.getAvailable();
		// 总消费积分
		long totalExpensePoint = pointAccount.getTotalExpensePoint();
		// 总过期积分
		long totalCountOverduePoint = pointAccount.getCountOverduePoint();
		// 总已过期积分（已处理）
		long totalOverduePoint = pointAccount.getTotalOverduePoint();

		// 本次清零积分
		long cleanPoint = cleanPointByCount(userNo, totalClearPoint);

		// 过期积分（真实处理）
		pointAccount.setTotalOverduePoint(pointAccount.getTotalOverduePoint() + cleanPoint);
		// 有效积分 小于 清零积分，清零金额=有效金额
		pointAccount.setBalance(pointAccount.getBalance() - cleanPoint);
		// 本次清零积分
		pointAccount.setCleanPoint(cleanPoint);

		log.info("积分账户:{}, 有效积分:{},清零操作：总过期积分：{}，总消费积分:{},本次清零积分:{}，总已过期积分[已处理]：{}", userNo, available,
				totalCountOverduePoint, totalExpensePoint, cleanPoint, totalOverduePoint);

		PointGrade pointGrade = pointGradeService.getSectionPoint(pointAccount);
		pointAccount.setGradeId(pointGrade.getId());

		getEntityDao().update(pointAccount);
		return pointAccount;
	}

	@Transactional
	public long cleanPointByCount(String userNo, long totalClearPoint) {
		PointAccount pointAccount = lockByUserNo(userNo);
		// 账户余额
		long available = pointAccount.getAvailable();
		// 总消费积分
		long totalExpensePoint = pointAccount.getTotalExpensePoint();
		// 总过期积分
		long totalCountOverduePoint = pointAccount.getCountOverduePoint() + totalClearPoint;
		// 总已过期积分（已处理）
		long totalOverduePoint = pointAccount.getTotalOverduePoint();

		// 本次清零积分
		long cleanPoint = 0L;
		if (totalCountOverduePoint > totalExpensePoint) {
			// 消费差值=总过期积分-总过期积分(已清零)-总消费积分；（未清零积分-总消费积分）
			long diffPoint = totalCountOverduePoint - totalOverduePoint - totalExpensePoint;
			// 消费差值 【大于】 有效积分
			if (diffPoint > available) {
				cleanPoint = available;
			} else {
				cleanPoint = diffPoint;
			}
		}
		log.info("积分账户:{},清零操作：总过期积分：{}，总消费积分:{},本次清零积分:{},总已过期积分[已处理]:{}", userNo, totalCountOverduePoint,
				totalExpensePoint, cleanPoint, totalOverduePoint);
		return cleanPoint;
	}

	@Override
	@Transactional
	public PointAccount pointFreeze(String userNo, String userName, long point) {
		checkPointValue(userNo, point);
		PointAccount pointAccount = lockByUserNo(userNo);
		if (pointAccount == null) {
			throw new BusinessException(userNo + "积分账户不存在,无法冻结");
		}
		pointAccount.setUserName(userName);
		pointAccount.setFreeze(pointAccount.getFreeze() + point);
		if (pointAccount.getBalance() - pointAccount.getFreeze() < 0) {
			throw new BusinessException(userNo + "积分余额不足");
		}
		getEntityDao().update(pointAccount);
		return pointAccount;
	}

	@Override
	@Transactional
	public PointAccount pointUnfreeze(String userNo, String userName, long point) {
		checkPointValue(userNo, point);
		PointAccount pointAccount = lockByUserNo(userName);
		if (pointAccount == null) {
			throw new BusinessException(userNo + "积分账户不存在,无法解冻");
		}
		if (pointAccount.getFreeze() < point) {
			throw new BusinessException(userNo + "冻结积分不足,无法解冻");
		}
		pointAccount.setUserName(userName);
		pointAccount.setFreeze(pointAccount.getFreeze() - point);
		getEntityDao().update(pointAccount);
		return pointAccount;
	}

	@Override
	public int pointRank(String userName, Long gradeId) {
		int pointRank = 1;
		if (gradeId != null) {
			pointRank = getEntityDao().pointRankByUserNoAndGradeId(userName, gradeId);
		} else {
			pointRank = getEntityDao().pointRankByUserNo(userName);
		}
		return pointRank;
	}

	@Override
	@Transactional
	public PointAccount lockByUserNo(String userNo) {
		return getEntityDao().lockByUserNo(userNo);
	}

	private void checkPointValue(String userNo, long point) {
		if (point <= 0) {
			throw new BusinessException(userNo + "交易不能小于1积分");
		}
	}

}
