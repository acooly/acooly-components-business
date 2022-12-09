/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 */
package com.acooly.module.point.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Ids;
import com.acooly.module.point.dao.PointTradeDao;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointTrade;
import com.acooly.module.point.enums.PointTradeType;
import com.acooly.module.point.service.PointAccountService;
import com.acooly.module.point.service.PointTradeService;
import com.acooly.module.point.service.PointTypeCountService;

import lombok.extern.slf4j.Slf4j;

/**
 * 积分交易信息 Service实现
 * <p>
 * <p>
 * Date: 2017-02-03 22:50:14
 *
 * @author cuifuqiang
 */
@Slf4j
@Service("pointTradeService")
public class PointTradeServiceImpl extends EntityServiceImpl<PointTrade, PointTradeDao> implements PointTradeService {

    @Autowired
    private PointAccountService pointAccountService;
    @Autowired
    private PointTypeCountService pointTypeCountService;

    @Autowired
    @Qualifier("commonTaskExecutor")
    private TaskExecutor taskExecutor;

    @Override
    @Transactional
    public PointTrade pointProduce(String userNo, String userName, long point, String overdueDate, PointTradeInfoDto pointTradeDto) {
        if (point <= 0L) {
            return null;
        }
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.produce, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }

        PointAccount pointAccount = pointAccountService.pointProduce(userNo, userName, point);
        PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.produce, overdueDate, pointTradeDto);
        return pointTrade;
    }

    @Override
    @Transactional
    public PointTrade pointExpense(String userNo, String userName, long point, boolean isFreeze, PointTradeInfoDto pointTradeDto) {
        if (point <= 0L) {
            return null;
        }
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.expense, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }

        // 正常消费积分
        PointAccount pointAccount = pointAccountService.pointExpense(userNo, userName, point, isFreeze);

        if (isFreeze) {
            saveTrade(pointAccount, point, PointTradeType.unfreeze, null, pointTradeDto);
        }
        PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.expense, null, pointTradeDto);
        return pointTrade;
    }

    @Override
    public PointTrade pointClear(String userNo, String userName, long totalClearPoint, PointTradeInfoDto pointTradeDto) {
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.overdue_clear, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }
        PointAccount pointAccount = pointAccountService.pointOverdueClear(userNo, userName, totalClearPoint);
        PointTrade pointTrade = saveTrade(pointAccount, pointAccount.getCleanPoint(), PointTradeType.overdue_clear, null, pointTradeDto);
        return pointTrade;
    }

    @Override
    public PointTrade pointExpenseByCancel(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto) {
        if (point <= 0L) {
            return null;
        }
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.cancel, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }

        PointAccount pointAccount = pointAccountService.pointExpenseByCancel(userNo, userName, point);
        PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.cancel, null, pointTradeDto);
        return pointTrade;
    }

    @Override
    @Transactional
    public PointTrade pointFreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto) {
        if (point <= 0L) {
            return null;
        }
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.freeze, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }

        PointAccount pointAccount = pointAccountService.pointFreeze(userNo, userName, point);
        PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.freeze, null, pointTradeDto);
        return pointTrade;
    }

    @Override
    @Transactional
    public PointTrade pointUnfreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto) {
        if (point <= 0L) {
            return null;
        }
        // 重复积分操作，幂等性校验
        PointTrade pointTradeDB = repeatTradeCheck(userNo, PointTradeType.unfreeze, pointTradeDto);
        if (pointTradeDB != null) {
            return pointTradeDB;
        }

        PointAccount pointAccount = pointAccountService.pointUnfreeze(userNo, userName, point);
        PointTrade pointTrade = saveTrade(pointAccount, point, PointTradeType.unfreeze, null, pointTradeDto);
        return pointTrade;
    }

    public long getProducePoint(String userNo, String startTime, String endTime) {
        Long countProducePoint = getEntityDao().getProducePoint(userNo, startTime, endTime);
        if (countProducePoint == null) {
            return 0;
        }
        return countProducePoint.longValue();
    }

    public long getExpensePoint(String userNo, String startTime, String endTime) {
        Long countExpensePoint = getEntityDao().getExpensePoint(userNo, startTime, endTime);
        if (countExpensePoint == null) {
            return 0;
        }
        return countExpensePoint.longValue();
    }

    @Override
    public long countProducePointByBusi(String userNo, String busiId, String busiType) {
        Long countProducePoint = getEntityDao().countProducePointByBusi(userNo, busiId, busiType);
        if (countProducePoint == null) {
            return 0;
        }
        return countProducePoint.longValue();
    }

    /**
     * 积分交易（业务系统自行计算积分）
     *
     * @param oldUserNo
     * @param oldUserName
     * @param tradePoint     交易积分
     * @param isFreeze       是否存在冻结（true:存在冻结积分,false:不存在冻结积分）
     *                       true：解冻后执行积分交易
     *                       false：直接执行积分交易
     * @param targetUserNo   目标用户号
     * @param targetUserName 目标用户名
     * @param pointTradeDto  业务数据
     * @return
     */
    @Override
    @Transactional
    public void pointTrade(String oldUserNo, String oldUserName, long tradePoint, boolean isFreeze, String targetUserNo, String targetUserName, PointTradeInfoDto pointTradeDto) {
        if (tradePoint <= 0L) {
            return;
        }
        //转出方
        pointExpense(oldUserNo, oldUserName, tradePoint, isFreeze, pointTradeDto);
        //接收方
        pointProduce(oldUserNo, oldUserName, tradePoint, null, pointTradeDto);
    }

    /**
     * @param pointAccount
     * @param point
     * @param tradeType
     * @param overdueDate   过期时间
     * @param pointTradeDto
     * @return
     */
    private PointTrade saveTrade(PointAccount pointAccount, long point, PointTradeType tradeType, String overdueDate, PointTradeInfoDto pointTradeDto) {
        PointTrade pointTrade = new PointTrade();
        pointTrade.setTradeNo(Ids.oid());
        pointTrade.setAccountId(pointAccount.getId());
        pointTrade.setUserNo(pointAccount.getUserNo());
        pointTrade.setUserName(pointAccount.getUserName());
        pointTrade.setTradeType(tradeType);
        pointTrade.setAmount(point);
        pointTrade.setEndFreeze(pointAccount.getFreeze());
        pointTrade.setEndBalance(pointAccount.getBalance());
        pointTrade.setEndAvailable(pointAccount.getAvailable());
        pointTrade.setEndDebtPoint(pointAccount.getDebtPoint());
        pointTrade.setOverdueDate(overdueDate);
        if (null != pointTradeDto) {
            pointTrade.setBusiId(pointTradeDto.getBusiId());
            pointTrade.setBusiType(pointTradeDto.getBusiType());
            pointTrade.setBusiTypeText(pointTradeDto.getBusiTypeText());
            pointTrade.setBusiData(pointTradeDto.getBusiData());
            pointTrade.setComments(pointTradeDto.getComments());
        }
        getEntityDao().create(pointTrade);

        // 保存统计信息
        if (pointTrade.getTradeType() == PointTradeType.produce) {
            pointTypeCountService.savePointTypeCount(pointTrade);
        }
        return pointTrade;
    }

    /**
     * 重复积分操作，幂等性校验
     */
    private PointTrade repeatTradeCheck(String userNo, PointTradeType pointTradeType, PointTradeInfoDto pointTradeDto) {
        PointTrade pointTradeDB = null;
        if (!pointTradeDto.isRepeatTrade()) {
            String busiId = pointTradeDto.getBusiId();
            String busiType = pointTradeDto.getBusiType();
            if (Strings.isBlank(busiId)) {
                throw new BusinessException("开启重复积分操作，busiId不能为空");
            }
            if (Strings.isBlank(busiType)) {
                throw new BusinessException("开启重复积分操作，busiType不能为空");
            }
            pointTradeDB = getEntityDao().findByUserNoAndBusiIdAndBusiType(pointTradeType.code(), userNo, pointTradeDto.getBusiId(), pointTradeDto.getBusiType());
        }
        return pointTradeDB;
    }

}
