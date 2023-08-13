业务组件库
=====

## 简介

业务组件库，主要是收集，封装常见的业务场景为可复用的组件，以便在项目中快速使用。

## 组件列表

| 组件名称                                                                                             | 组件说明                                                                                                                           |
|--------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------|
| [acooly-component-account：账务组件](./acooly-component-account/README.md)                            | 根据多个项目的需求和经验，抽象封装账务相关的基础能力，以实现基础账务的实现统一，标准，安全可控。主要包含的账户及余额管理，进出账流水记账，记账交易码的定义管理，基础统计分析。对外提供统一的接口提供标准开户，单笔记账，批量记账，单笔/批量转账等核心能力。 |
| [acooly-component-member：会员组件](./acooly-component-member/README.md) | 会员组件是根据多个项目的实践积累和抽取，封装的通用会员业务体系组件。涵盖了会员相关的基础功能和通用信息管理，可作为具体业务项目的会员中心基础，也可作为CRM的基础核心模块。                                         |
|[acooly-component-assetmgmt：资产管理](./acooly-component-assetmgmt/README.md)| 提供简单通用的资产管理工具，提供对公司各内资产，设备的管理|
|[acooly-component-countnum：游戏计数组件](./acooly-component-countnum/README.md)|采用redis缓存机制，支持高并发的计数类（游戏）组件；|
|[acooly-component-lottery：抽奖组件](./acooly-component-lottery/README.md)|通用抽奖组件提供了常规抽奖活动的逻辑封装和配置|
|[acooly-component-point：积分组件](./acooly-component-point/README.md)|积分业务组件，积分等级设置,积分账户管理, 积分交易记录, 积分统计清零, 积分规则设置,积分业务统计。满足场景：业务系统对接方案简单，快速搭建积分账户系统|
|[acooly-component-redpack：红包组件](./acooly-component-redpack/README.md)|分布式红包逻辑组件,采用redis缓存机制，支持 红包创建，红包发放，红包退款标记；|

