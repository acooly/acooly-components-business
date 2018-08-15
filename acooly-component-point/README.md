acooly-component-point(积分组件)
====

## 简介

acooly-component-point以acooly框架为基础开发的积分业务组件

## 接口说明：

	1：积分账户查询 
	com.acooly.point.service.PointAccountService#findByUserName(String userName);
	
	2：积分产生 
	com.acooly.point.service.PointTradeService#pointProduce(String userName, long point, PointTradeDto pointTradeDto);
	
	3：积分消费 
	com.acooly.point.service.PointTradeService#pointExpense(String userName, long point, boolean isFreeze, PointTradeDto pointTradeDto);;
	
	4：积分冻结 
	com.acooly.point.service.PointTradeService#(String userName, long point, PointTradeDto pointTradeDto);
	
	5：积分解冻 
	com.acooly.point.service.PointTradeService#pointUnfreeze(String userName, long point, PointTradeDto pointTradeDto);

	6：积分等级排名（gradeId为空：所有用户排名；gradeId不为空：同等级用户排名）
	com.acooly.point.service.PointAccountService#pointRank(String userName, Long gradeId)

	7：获取即将清零积分
	com.acooly.module.point.service#getClearPoint(String userName, Date tradeTime)


##组件设置
组件名称(积分，经验值等：默认名称：积分)

acooly.point.pointModuleName=积分

##版本说明
####v2:2018-08
1.新增根据积分产生的业务类型进行分类统计

2.支持自定义组件模块的名称，默认名称：积分

3.对于积分操作（产生，消费，冻结，解冻） 新建 事务处理，支持高并发处理

####v1:2017-02
积分组件基本功能：积分账户查询 ，积分产生，积分消费，积分冻结，积分解冻 

支持一定时间区间类统计清空积分




