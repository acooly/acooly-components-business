<!-- title:积分组件 -->
<!-- type: business -->
<!-- author: cuifuqiang -->
<!-- date: 2018-12-26 -->
## 简介

acooly-component-point以acooly框架为基础开发的积分业务组件; 

核心模块说明： 积分等级设置,积分账户管理, 积分交易记录, 积分统计清零, 积分规则设置,积分业务统计

满足场景：业务系统对接方案简单，快速搭建积分账户系统

##版本说明（重要，重要）

	新版本：
	<dependency>
	  <groupId>com.acooly</groupId>
	  <artifactId>acooly-component-point</artifactId>
	  <version>5.0.0-SNAPSHOT</version>
	</dependency>
	为了不影响历史版本，新版数据库表前缀：pt_point_
	


	2022年2月22号 之前的搭建的业务系统，如不升级，请使用历史版本（不再更新维护）
	历史版本：
	<dependency>
	  <groupId>com.acooly</groupId>
	  <artifactId>acooly-component-point</artifactId>
	  <version>5.0.0-SNAPSHOT-20220222</version>
	</dependency>
	
	数据库表前缀：point_

## 核心功能
* 1. 积分等级设置(必须)，如果业务系统对积分等级没有需求，建议等级区间0到无限大

* 2. 积分规则设置说明，详细参考 “配置方案提示”

* 3.【积分清零】积分组件产生的积分支持到期时间，到期时间后为使用的积分会清零（统计用户到期时间的积分，根据规则清零对应的积分）

* 4.【积分清零】积分组件不涉及到定时任务，因此积分清零的定时任务为：com.acooly.module.point.service.PointStatisticsService#pointByCountAndClear;默认处理前一天的


##组件设置

	/** 是否启用 **/
	private boolean enable = true;

	/** 组件名称(积分，经验值等：默认名称：积分) */
	private String pointModuleName = "积分";

	/**
	 * 积分账户等级-计算等级依据标识
	 * <li>totalPoint 用户总积分
	 * <li>balancePoint 用户余额积分
	 */
	private String pointGradeBasis = "totalPoint";

	/**
	 * 设置负债账户
	 * <li>true: 设置负债账户
	 * <li>false：不设置负债账户
	 * <li>场景描述：用户在平台商城购买商品后，产生了100个积分，在积分商城使用100积分兑换商品后并已收货，用户再平台商城退货，此时需要扣除用户100积分，及负债账户：100
	 */
	private boolean setDebt = false;

	/**
	 * 积分系统-业务系统业务扩充枚举
	 * <li>当业务系统定义的业务场景枚举无法覆盖实际场景，支持枚举扩充（例如：用户奖励，客服奖励等等）
	 * <li>格式：acooly.point.pointBusiTypeEnum[user_reward]=优秀用户奖励
	 */
	private Map<String, String> pointBusiTypeEnum = Maps.newHashMap();

	/**
	 * 业务系统定义的业务类型，
	 * <li>完整的java类路径，必须实现Messageable，请参考acooly相关文档, 重新toString方法[ 格式如下：String.format("%s:%s", this.code, this.message);]
	 * <li>
	 * 
	 * <li>在积分系统场景应用：
	 * <li>1. 积分账户-发放积分，对应的业务场景
	 * <li>2. 配置积分规则，基于业务场景区分
	 * <li>格式：acooly.point.busiTypeEnumClassName=com.acooly.module.point.enums.PointBusinessTypeEnum
	 **/
	private String busiTypeEnumClassName;

	



## 接口说明：

. 积分业务服务(积分系统统一入口)：com.acooly.module.point.business.PointBusinessService


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
	



##版本说明

####v3:2022-03
1. 重新升级 表结构，新增积分规则设置
2. 重构 积分清零规则，易用性加强
3. 支持动态业务规则解析，便于业务系统 积分统计使用


####v2:2019-06
1.积分等级支持等级图标上传
2.优化积分等级默认图标

####v2:2018-08
1.新增根据积分产生的业务类型进行分类统计

2.支持自定义组件模块的名称，默认名称：积分

3.对于积分操作（产生，消费，冻结，解冻） 新建 事务处理，支持高并发处理

####v1:2017-02
积分组件基本功能：积分账户查询 ，积分产生，积分消费，积分冻结，积分解冻 

支持一定时间区间类统计清空积分




