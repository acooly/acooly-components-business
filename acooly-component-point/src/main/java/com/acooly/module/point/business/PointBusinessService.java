package com.acooly.module.point.business;

import com.acooly.core.utils.Money;
import com.acooly.module.point.dto.PointTradeInfoDto;
import com.acooly.module.point.entity.PointAccount;
import com.acooly.module.point.entity.PointTrade;
import org.springframework.transaction.annotation.Transactional;

/**
 * 积分业务服务(积分系统统一入口)
 *
 * @author CuiFuQ
 * <p>
 * 20220222
 */
public interface PointBusinessService {

    /**
     * 查询积分账户
     *
     * @param userNo
     * @return
     */
    public PointAccount findByUserNo(String userNo);

    /**
     * 同步积分账户信息(变更用户名)
     * <li>如果无积分账户，会创建一个积分账户
     *
     * @param userNo
     * @param userName
     * @return
     */
    public PointAccount syncPointAccount(String userNo, String userName);


    /**
     * 计算用户本次真实清零的积分
     *
     * @param userNo
     * @param totalClearPoint 本次预计清零积分
     * @return
     */
    public long cleanPointByCount(String userNo, long totalClearPoint);

    /**
     * 根据积分规则产生相应的积分，积分获取个数由积分规则控制
     * <li>amount为交易金额(单位：元)，仅支持整除，余数自动舍弃
     * <li>pointTradeDto中busiTypeEnum需要配置积分规则中
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param amount        业务系统-交易金额(单位元)，非交易金额业务场景:(如：签到等)，填写0L
     * @param pointTradeDto 业务数据
     */
    public PointTrade pointProduceByRule(String userNo, String userName, Money amount, PointTradeInfoDto pointTradeDto);

    /**
     * （业务退款-积分取消）根据业务属性找到对应的积分记录，做扣除操作；
     * <li>pointProduceByRule(...pointTradeDto(busiId【必填】,busiType【必填】))
     *
     * @param userNo       用户号
     * @param busiId       取消积分时，业务系统 busiId
     * @param busiType     取消积分时，业务系统定义业务类型
     * @param busiComments 取消积分时，业务系统备注
     * @return
     */
    public PointTrade pointExpenseByRule(String userNo, String busiId, String busiType, String busiComments);

    /**
     * 积分产生（业务系统自行计算积分）
     *
     * @param userNo        用户号
     * @param userName      用户名
     * @param point         交易积分(产生)
     * @param overdueDate   过期时间，格式：yyyy-MM-dd; 第二天清零前一天的积分
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
    public void pointTrade(String oldUserNo, String oldUserName, long tradePoint, boolean isFreeze, String targetUserNo, String targetUserName, PointTradeInfoDto pointTradeDto);

}
