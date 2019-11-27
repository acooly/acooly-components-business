<!-- title: 红包组件 -->
<!-- type: business -->
<!-- author: cuifuqiang -->
acooly-component-redpack(红包组件)
====

## 简介

acooly-component-point以acooly框架为基础,分布式红包逻辑组件,采用redis缓存机制，支持 红包创建，红包发放，红包退款标记；

1、支持redis分布式锁多机方式，高并发实现方案

2、自服务内聚所有红包功能，实现：红包策略的配置，定义，发放，算法，记录和查询

3、采用事件机制进行消息同步，业务系统解耦

## 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-redpack</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

## 对接说明（重要）

* 订阅 红包事件  RedPackEvent,可以参考  *

	com.acooly.module.redpack.business.event.handle.RedPackEventHandle
	
* 订阅 红包订单事件（红包算法记录）  RedPackOrderEvent,可以参考  *

	com.acooly.module.redpack.business.event.handle.RedPackOrderEventHandle	
	
* 业务系统红包订单记录标记  RedPackOrderEvent,可以参考  *
	
	/**红包支付中**/
	com.acooly.module.redpack.business.service.RedPackReplyTradeService#redPackPaying
	
	/**红包支付完成**/
	com.acooly.module.redpack.business.service.RedPackReplyTradeService#redPackPaySuccess
	
		
	

## 接口说明

####	接口类：com.acooly.module.redpack.business.service.RedPackTradeService
* 相关红包发送功能 *
	
	/**
	 * 查询红包
	 * 
	 * <li>redis缓存
	 * 
	 * @return
	 */
	RedPackDto findRedPack(Long redPackId);

	/**
	 * 创建红包
	 * 
	 * <li>redis缓存
	 * <li>发布事件 RedPackEvent
	 * 
	 * @return
	 */
	RedPackDto createRedPack(CreateRedPackDto dto);

	/**
	 * 发送红包
	 * 
	 * <li>redis缓存
	 * <li>发布事件：RedPackOrderEvent
	 * 
	 * @return
	 */
	RedPackOrderDto sendRedPack(SendRedPackDto sendRedPackDto);

	/**
	 * 红包退款
	 * 
	 * <li>redis缓存
	 * <li>发布事件：RedPackOrderEvent
	 * 
	 * @return
	 */
	RedPackOrderDto refundRedPack(Long redPackId);

	/**
	 * 查询红包列表
	 * 
	 * <li>redis缓存
	 * 
	 * @return
	 */
	List<RedPackOrderDto> findRedPackOrder(Long redPackId);
	
	
	/**
	 * 查询红包列表
	 *
	 * @param redPackId 红包id
	 * @param sort      true:降序(从大到小);flase:升序(从小到大);null:（默认id倒序）
	 * @return
	 */
	List<RedPackOrderDto> findRedPackOrderSort(Long redPackId, Boolean sort);


####	接口类：com.acooly.module.redpack.business.service.RedPackReplyTradeService
* 红包发送成功，业务回调使用 *
	
	/**
	 * 
	 * 红包支付中
	 * 
	 * <li>业务系统标记红包记录
	 * 
	 * @return
	 */
	RedPackOrder redPackPaying(Long redPackOrderId);

	/**
	 * 
	 * 红包支付完成
	 * 
	 * <li>业务系统标记红包记录
	 * <li>红包状态变更发送 红包事件【RedPackEvent】
	 * 
	 * @return
	 */
	RedPackOrder redPackPaySuccess(Long redPackOrderId);
	
	


##组件设置

	/** 红包分布式锁Key */
	acooly.redPack.redPackDistributedLockKey=red_pack_lock_key
	
	/** 红包分布式 redis缓存时间(系统默认 最少设置20分钟) */
	acooly.redPack.redPackRedisTime=20


##版本说明
####v1:2019-10
- 1.发红包，新增 扩展数据Map<String, Object> dataMap（如头像,昵称）,并持久化，支持数据库缓存
- 2.优化redis缓存方案 tryLock 优化
- 3.新增红包游戏过期时间设置


####v1:2019-09
新增功能支持，红包过期后发送 红包过期事件，RedPackOverdueEvent

####v1:2019-05
新增红包订单查询接口(findRedPackOrderSort(Long redPackId, Boolean sort))，并支持 金额排序

####v1:2019-03
积分组件基本功能：红包创建（领取次数，过期时间，红包总额，红包数量，红包状态），红包发放，红包查询，红包订单查询





