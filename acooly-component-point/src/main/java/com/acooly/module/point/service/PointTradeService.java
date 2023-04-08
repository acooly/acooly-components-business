/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by cuifuqiang
 * date:2017-02-03
 *
 */
package com.acooly.module.point.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointTrade;

/**
 * 积分交易信息 Service接口
 * <p>
 * <p>
 * Date: 2017-02-03 22:50:14
 *
 * @author cuifuqiang
 */
public interface PointTradeService extends EntityService<PointTrade> {

    /**
     * 积分产生
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(产生)
     * @param overdueDate   过期时间，格式：yyyy-MM-dd; 为空 :无限期； 第二天清零前一天的积分
     * @param pointTradeDto 业务数据
     */
    public PointTrade pointProduce(String userNo, String userName, long point, String overdueDate,
                                   PointTradeInfoDto pointTradeDto);

    /**
     * 积分消费
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(消费)
     * @param isFreeze      是否存在冻结（true:存在冻结积分,false:不存在冻结积分）
     * @param pointTradeDto 业务数据
     * @return
     */
    public PointTrade pointExpense(String userNo, String userName, long point, boolean isFreeze,
                                   PointTradeInfoDto pointTradeDto);

    /**
     * @param userNo
     * @param userName
     * @param totalClearPoint 本次清零积分
     * @return
     */
    public PointTrade pointClear(String userNo, String userName, long totalClearPoint, PointTradeInfoDto pointTradeDto);

    /**
     * 积分消费--取消
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(消费)
     * @param pointTradeDto 业务数据
     * @return
     */
    public PointTrade pointExpenseByCancel(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto);

    /**
     * 积分冻结
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(冻结积分)
     * @param pointTradeDto 业务数据
     * @return
     */
    public PointTrade pointFreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto);

    /**
     * 积分解冻
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(解冻积分)
     * @param pointTradeDto 业务数据
     * @return
     */
    public PointTrade pointUnfreeze(String userNo, String userName, long point, PointTradeInfoDto pointTradeDto);

    /**
     * 获取某一时间产生积分
     *
     * @param userNo
     * @param startTime
     * @param endTime
     * @return
     */
    public long getProducePoint(String userNo, String startTime, String endTime);

    /**
     * 获取某一时间产生消耗
     *
     * @param userNo
     * @param startTime
     * @param endTime
     * @return
     */
    public long getExpensePoint(String userNo, String startTime, String endTime);

    /**
     * 根据业务类型统计用户累计积分
     *
     * @param userNo
     * @param startTime
     * @param endTime
     * @return
     */
    public long countProducePointByBusi(String userNo, String busiId, String busiType);

    /**
     * 积分交易（业务系统自行计算积分）
     *
     * @param sourceUserNo
     * @param sourceUserName
     * @param tradePoint     交易积分
     * @param isFreeze       是否存在冻结（true:存在冻结积分,false:不存在冻结积分）
     *                       true：解冻后执行积分交易
     *                       false：直接执行积分交易
     * @param targetUserNo   目标用户号
     * @param targetUserName 目标用户名
     * @param pointTradeDto  业务数据
     * @return
     */
    void pointTrade(String sourceUserNo, String sourceUserName, long tradePoint, boolean isFreeze, String targetUserNo, String targetUserName, PointTradeInfoDto pointTradeDto);
}
