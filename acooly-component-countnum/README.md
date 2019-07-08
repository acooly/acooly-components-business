<!-- title: 游戏-计数组件 -->
<!-- type: business -->
<!-- author: cuifuqiang -->
acooly-component-countnum(游戏-计数组件)
====

## 简介

acooly-component-countnum以acooly框架为基础,采用redis缓存机制，支持高并发的计数类（游戏）组件；

1、支持redis分布式锁多机方式，高并发实现方案

2、自服务内聚游戏计数功能，支撑时间限制类型，数量限制类游戏计数；根据游戏类型快速排序

3、采用事件机制进行消息同步，业务系统解耦

## 组件运用场景

微信小程序，web小程序 计数类游戏场景；

### 游戏场景

##### 时间限制类游戏

数钱:用户在30秒内，数钱数量（手指滑动屏幕次数）

打地鼠：用户在60秒内，打地鼠数量（手指点击屏幕次数）

##### 时间限制类游戏

数钱:用户数完100元，所需要的时间（手指滑动屏幕次数）

打气球：用户戳破屏幕中100个气球，所需要的时间（手指点击屏幕次数）



## 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-countnum</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

	
	

## 接口说明

####	接口类：com.acooly.module.countnum.business.service.CountNumGameService
	
		/**
	 * 查询计数游戏
	 * 
	 * @return
	 */
	CountNumGameDto findCountNum(Long countNumId);

	/**
	 * 创建计数游戏
	 * 
	 * @return
	 */
	CountNumGameDto createCountNumGame(CreateCountNumGameDto dto);

	/**
	 * 提交游戏结果
	 * 
	 * @return
	 */
	CountNumGameOrderDto submitCountNumGameResult(CountNumGameResultDto countNumGameResultDto);

	/**
	 * 计数游戏结束(立即结束)
	 * 
	 * <li>游戏结束后，不再处理提交的游戏结果
	 * 
	 * @return
	 */
	CountNumGameDto countNumGameFinish(long countNumId);

	/**
	 * 计数游戏结束(到达过期时间，自动结束)
	 * 
	 * <li>游戏结束后，不再处理提交的游戏结果
	 * 
	 * @return
	 */
	CountNumGameDto countNumGameOverdueFinish(long countNumId, Date overdueDate);

	/**
	 * 查询计数游戏记录列表
	 * 
	 */
	List<CountNumGameOrderDto> findCountNumGameOrder(long countNumId);

	/**
	 * 用户游戏排名(返回用户名次)
	 * 
	 * @param userId
	 * @param countNumId
	 * @param isOverstep
	 * 
	 *                   true:计算用户排名比例，false:不计算比例）
	 * @return
	 */
	CountNumGameOrderRankDto userRanking(long userId, long countNumId, boolean isOverstep);

	/**
	 * 统计用户参与的次数
	 * 
	 * @param userId
	 * @param countNumId
	 * @return
	 */
	Long countNumGameOrderNum(long userId, long countNumId);

	


##组件设置

	/** 计数类游戏-分布式锁Key */
	private String countNumDistributedLockKey = "game_count_num_key";

	/** 计数类游戏-分布式 redis缓存时间(最少设置10分钟) */
	private Long countNumRedisTime = 10L;

	/** 计数类游戏-布式 redis缓存记录条数 */
	private Long countNumRedisOrderNum = 50L;


##版本说明


####v1:2019-07
组件基本功能：游戏创建，上传游戏结果，游戏结束（控制），游戏记录查询（已排序，限制缓存记录数）





