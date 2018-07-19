账务组件：account-component-account
====

## 简介
根据多个项目的需求和经验，抽象封装账务相关的基础能力，以实现基础账务的实现统一，标准，安全可控。主要包含的账户及余额管理，进出账流水记账，记账交易码的定义管理，基础统计分析。对外提供统一的接口提供标准开户，单笔记账，批量记账，单笔/批量转账等核心能力。

## 特性

* 交易码定义和管理，包括：内置，管理界面自定义和集成系统扩展定义三部分的整合，接口：TradeCode
* 账户的定义和开户，包括与userId，userNo及accountType的关系。
* 支持对账户体系，通过accountType区分，默认账户accountType=main
* 支持账户的基本功能：开户，单笔/批量记账，单笔/批量转账，冻结/解冻，同时记录对应的流水
* 批量记账的单次最大笔数可参数化指定(batchMaxSize)。
* 批量记账会记录（默认可生成，也可传入）本次批量记账的批次号，便于与上层业务关联。
* 每次记账及记账的包装功能（转账，冻结等）都支持默认记账交易码，也可以由调用方指定.
* [待实现]：账务分析，包括: 根据交易码的日统计分析报表和图形
* [待实现]：热点账户的解决方案，表外异步账的实现。
* [待实现]：冻结码管理

## 集成

通用组件集成模式，通过pom引用，默认为开启状态。参数配置前缀：acooly.account.

## 设计

### 核心关系设计

* 每个账户都有accountId（主键ID物理标志）和accountNo（逻辑编码标志）两个唯一标志。
* 每个账户都有userId（物理ID）和userNo（逻辑编码）两个用户标志属性。
* 支持每个用户多账户，通过accountType区分，默认账户类型为main。userId/userNo+accountType可唯一确定一个账户。一般默认账户的accountId=userId, accountNo=userNo

### 交易码设计

组件定义了专用的TradeCode接口，用于标志账务流水的交易码。

```java
public interface TradeCode {
    String code();
    String message();
    DirectionEnum direction();
    default String comments() {
        return null;
    }
    default String lable() {
        return code() + "/" + message() + "/" + direction();
    }
}
```
TradeCode的来源：

1. 组件内置常规的通用交易码：CommonTradeCodeEnum 。主要包括：deposit，withdraw，freeze，transfer等。
2. 组件后台BOSS管理界面维护的可配置的交易码：AccountTradeCode。一般通过转换为DefaultTradeCode使用。
3. 可以在目标集成工程内，构建一个枚举（TradeCode）来使用，同时构建一个类实现TradeCodeLoader接口返回TradeCode集合。类型于下面的代码：

	```java
	@Component
	public class CommonTradeCodeLoader implements TradeCodeLoader {

	    @Override
	    public List<TradeCode> loadTradeCodes() {
	        List commonTradeCodeEnums = CommonTradeCodeEnum.getAll();
	        return commonTradeCodeEnums;
	    }
	}
	```




## 应用

### 核心接口

```java
// 账户管理服务
AccountManageService
// 账务交易服务
AccountTradeService
// 账务校验服务
AccountVerifyService
// 分页查询
com.acooly.component.account.manage.Account${...}Service
// 交易码扩展接口
TradeCodeLoader
```
详细接口，方法和参数说明请参考源代码的javadoc

### 核心DTO

#### AccountInfo

用于定义账户信息的DTO，主要用于定位用户的参数集合和组织创建账户的基本信息。

* 用户创建账户

```java
// 创建userId作为默认accountId，userNo作为默认accountNo的构造
public AccountInfo(Long userId, String userNo)
```

```java
/**
* 用户创建账户：指定所有核心参数
* <p>
* 如果不传入accountId则系统生成
* 如果不传入accountNo则系统生成
*/
public AccountInfo(@Nullable Long accountId, @Nullable String accountNo, Long userId, String userNo, AccountTypeEnum accountType) 
```

通过以上构造完成AccountInfo的创建后，可选设置username和comments属性。详情请参考组件源代码：AccountInfo

* 定位账户

```java
    // 用于定位账户 with Id
    public AccountInfo(Long accountId)

    //用于定位账户 with No
    public AccountInfo(String accountNo)
```

可以通过以上两个构造来创建AccountInfo,用于查询，定位和以account标志为基础的账务操作。


#### AccountKeepInfo

记账信息Dto, 用于完整描述一条记账请求数据。AccountKeepInfo是AccountInfo的子类，用于表述制定账户的记账信息，除了AccountInfo的账户信息外，主要增加：TradeCode, amount, busiId, busiData, batchNo等。

详细的构造记账信息的方法请参考该DTO的构造方法及javadoc说明。

#### TransferInfo

TransferInfo是专用于转账的DTO，是对AccountKeepInfo的补充，主要描述的是两元记账信息。表述的是从某个账户转账到另外一个账户的信息情况。

### 账户管理接口

#### 开户

```java
/**
* 独立开户
* /
Account openAccount(AccountInfo accountInfo);
```
开户是特别需要注意的接口，关系到与你的会员体系的整合。一般来说，我们是现有会员系统，然后在会员系统基础上关联账务系统。所以，可能存在的情况如下：

1. 用户ID和用户编码与账户ID和账户编码一致，一般用于默认账户，方便业务关联和管理。同时账户ID不采用数据库自增长方式生成，而是由userId方式指定。请参考单元测试：com.acooly.component.account.AccountTradeServiceTest的testOpenAccountWithUserIdEquelsAccountId方法。
2. 用户ID和用户编码与账户ID和编码不一致，并通过accountType区分账户唯一性。需要对AccountInfo传入userId和userNo，但设置accountId和accountNo（也可以手动设置）为空，由账务组件自动生成。详细用法请参考单元测试：AccountTradeServiceTest.testOpenAccountByUserId()方法。

#### 暂停/启用/禁用

待开发（其实是忘记了...）

#### 查询账户

```java
Account loadAccount(AccountInfo accountInfo);
```

### 账务交易接口

注意：所有的批量记账（包括转账和批量冻结/解冻）都会返回本次批量的批次号，可用于与上层业务关联。批次号也可以通过AccountKeepInfo传入，但必须保持本批次所有记录一致。

#### 单笔记账

```java
    /**
     * 单笔记账
     * 用于单边交易记账，如：充值，提现，冻结等
     *
     * @param accountTradeInfo
     */
    void keepAccount(AccountKeepInfo accountTradeInfo);
```


#### 批量记账


```java
    /**
     * 批量记账
     * <p>
     * 用户多变交易记账，如：付款等
     * 逻辑为：按传入list列表的顺序执行
     *
     * @param accountTradeInfos
     */
    String keepAccounts(List<AccountKeepInfo> accountTradeInfos);

    /**
     * 批量记账
     * <p>
     * 用户多变交易记账，如：付款等
     * 逻辑为：按传入list列表的顺序执行
     *
     * @param accountTradeInfos
     * @param comments
     * @return
     */
    String keepAccounts(List<AccountKeepInfo> accountTradeInfos, @Nullable String comments);

```

#### 转账

```java
    /**
     * 单笔转账
     *
     * @param transferInfo
     * @return batchNo
     */
    String transfer(TransferInfo transferInfo);

    /**
     * 批量自由转账
     * <p>
     * 默认最大600
     *
     * @param transferInfos
     * @return batchNo
     */
    String transfer(List<TransferInfo> transferInfos);

```


#### 冻结/解冻

```java
    /**
     * 冻结
     *
     * @param accountId
     * @param comments  可为空
     */
    void freeze(Long accountId, Money amount, @Nullable String comments);

    /**
     * 批量冻结
     *
     * @param accountIds
     * @param comments   可为空
     * @return batchNo
     */
    String freeze(List<Long> accountIds, Money amount, @Nullable String comments);

    /**
     * 解冻
     *
     * @param accountId
     * @param comments  可为空
     */
    void unfreeze(Long accountId, Money amount, @Nullable String comments);

    /**
     * 批量解冻
     *
     * @param accountIds
     * @param comments   可为空
     * @return batchNo
     */
    String unfreeze(List<Long> accountIds, Money amount, @Nullable String comments);

```








