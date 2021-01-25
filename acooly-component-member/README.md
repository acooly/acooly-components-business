<!-- title:会员组件 -->
<!-- type: business -->
<!-- author: zhangpu -->
<!-- date: 2018-12-30 -->
会员组件 acooly-component-member
====

## 1. 组件介绍

会员组件是根据多个项目的实践积累和抽取，封装的通用会员业务体系组件。涵盖了会员相关的基础功能和通用信息管理，可作为具体业务项目的会员中心基础，也可作为CRM的基础核心模块。

### 1.1. 特性

* 会员的注册：注册/短信激活（发送）/邮件激活（发送）/
* 会员级联开account账户
* 多级会员管理：parentId,parentNo和搜索专用path(/pid/pid/pid/)
* 会员手机管理：绑定/修改/找回等
* 会员密码安全：修改/重置/找回
* 个人会员实名认证和解析
* 会员业务关联：经纪人/介绍人/管理人
* 会员外部注册来源标记
* 会员自定义业务分类，通过接口注入方式扩展
* [todo] 企业会员实名认证和解析 
* [doing] 会员多操作员管理和认证

### 1.2. 设计

#### 1.2.1. 核心关系设计

* 每个会员都有id（主键ID物理标志）和userNo（逻辑编码标志）两个唯一标志。
* 全局username唯一（逻辑标志）

#### 1.2.2. 核心实体关系

* Member(会员实体) ---(1对1)---> MemberProfile(会员配置实体) 
* Member(会员实体) ---(1对1)---> MemberPersonal(个人会员信息实体) 
* Member(会员实体) ---(1对1)---> MemberEnterprise(企业或个体会员信息实体) 
* Member(会员实体) ---(1对1)---> MemberContact(会员联系信息实体) 
* [doing] Member(会员实体) ---(1对N)---> MemberAuth(会员认证信息，也可以作为操作员) 

## 2. 使用说明

### 2.1. 集成

通用组件集成模式，通过pom引用，默认为开启状态。参数配置前缀：acooly.account.

### 2.2. 配置
请参考acooly-components-business的测试模块配置。如下：

```ini
##### config for member ###########
acooly.member.enable=true
## 基础参数
# [可选] 注册时是否开启自动实名（默认：false）
acooly.member.realNameOnRegistry=false
# [可选] 注册是是否自动创建账户（默认：false）
acooly.member.accountRegisty=false
# [可选] 经纪人是否必须是会员（默认：true）
acooly.member.brokerMustBeMember=true
# [可选] 介绍人/推荐人是否必须是会员（默认：false）
acooly.member.inviterMustBeMember=false
# [可选] 注册激活验码有效期，该参数会替换掉 acooly.captcha.expireSeconds
acooly.member.captchaTimeoutSeconds=600
# [可选] 会员激活成功是否发邮件（默认：true）
acooly.member.sendMailOnActiveSuccess=true
# [可选] 会员激活成功是否发短信（默认：true）
acooly.member.sendSmsOnActiveSuccess=true
# [可选] 会员默认头像（内置）
acooly.member.defaultAvatar=/assets/default_avatar@64.png

# [可选] 会员相关短信模板配置,不配置则使用组件内默认（map，key的可选值为：MemberTemplateEnum）
acooly.member.smsTemplates.common=您本次{action}的验证码是：${captcha}, 用户名：${username}。
acooly.member.smsTemplates.register=您本次{action}的验证码是：${captcha}, 用户名：${username}。
acooly.member.smsTemplates.registerQuick=
acooly.member.smsTemplates.active=
acooly.member.smsTemplates.changePassword=

# [可选] 会员相关邮件模板文件名称配置（路径固定为：板配置的是ftl的文件名，目录在 /resource/mail/*.ftl）,
# 不配置则使用组件内默认（mailmap，key的可选值为：MemberTemplateEnum）
acooly.member.mailTemplates.common=member_common
acooly.member.mailTemplates.register=member_active_demo。
acooly.member.mailTemplates.active=member_active_success
acooly.member.mailTemplates.changePassword=member_changePassword

## [可选] 管理相关参数
acooly.member.manage.allowCreate=false
## [可选] 显示关联账户按钮
acooly.member.manage.showAccount = false;

## dependence centification component if necessary.
acooly.certification.provider=ali
acooly.certification.realnameurl=http://idcard.market.alicloudapi.com
acooly.certification.realname.appCode=63ab3d25a7b1******6f4dd54acdf6
acooly.certification.bankcert.appCode=1e02fbf636cd******1bdbbd929dc149

## [必选] 验证码生成器 captcha
# 验证码长度
acooly.captcha.captchaLength=6
# 验证码有效期（秒）
acooly.captcha.expireSeconds=900
# 验证码生成算法（数字，数字+字母...）
acooly.captcha.generatorType=random_number

## [必选] sms
acooly.sms.provider=mock
acooly.sms.cloopen.accountId=8aaf07085a3c*****44aee64302f2
acooly.sms.cloopen.accountToken=54b8454ee795*****f386582305b7fe
acooly.sms.cloopen.appId=8aaf07085b8e61*****b8f6274f4011

## [必选] email
acooly.mail.debug=true
acooly.mail.fromAddress=sender@******.cn
acooly.mail.fromName=Acooly技术服务团队
acooly.mail.hostname=smtp.mxhichina.com
acooly.mail.username=sender@*****.cn
acooly.mail.password=*******
        
#### end member config ##############

```

>ps: 需要特别注意的是：无论是否打开发送短信，邮件和验证码的功能，短信，邮件和验证码生成器这三个组件都是必须依赖的并需要进行对应的配置。

...

### 2.3. 核心接口及功能

* 会员基础服务，主要提供和封装核心：注册，激活和状态管理相关的那些事~ 接口：com.acooly.module.member.service.MemberService
* 会员发送服务：发送短信和邮件的业务封装。接口：com.acooly.module.member.service.MemberSendingService
* 会员安全服务：登录/验证，密码管理等。接口：com.acooly.module.member.service.MemberSecurityService
* 实名认证服务：com.acooly.module.member.service.MemberRealNameService


### 2.4. 组件自定义和扩展

#### 2.4.1. 注册逻辑扩展

**场景：**主要应对具体业务场景中，会员组件提供的信息不能完全满足业务，需要根据业务扩展自定义的会员扩展表，并在注册等业务逻辑处，需要自定义处理逻辑和数据。

**方案：**通过会员组件注册的拦截器或事件进行扩展。

注册成功事件：com.acooly.module.member.service.event.MemberRegistryEvent，在注册成功后（事务已提交），发布该事件，你可以在你的系统中直接订阅处理（同步或异步）

注册拦截器：不同于注册成功事件，拦截器会在注册的整个处理生命周期中，分阶段拦截调用。你只需要在你的系统中实现该拦截器，则可以控制整个注册主体逻辑（慎重）

com.acooly.module.member.service.interceptor.MemberRegistryInterceptor

```java
/**
 * 注册相关的扩拦截展器
 * <p>
 * <li>集成系统可根据实际的需求，实现拦截器扩展注册相关的能力</li>
 * <li>集成系统在拦截方法中抛出异常（MemberOperationException）则终止注册</li>
 * <li>集成系统如果在拦截方法中使用异步处理则不影响主注册流程</li>
 *
 * @author zhangpu 2018-07-26 17:46
 */
public interface MemberRegistryInterceptor {

    /**
     * 开始注册时
     * 发布点：已完成参数和基本逻辑坚持，注册开始时
     *
     * @param memberRegistryData
     */
    void beginRegistry(MemberRegistryData memberRegistryData);


    /**
     * 完成注册时
     * <p>
     * 注册逻辑完成，但还未提交数据库时
     *
     * @param memberRegistryData
     */
    void endRegistry(MemberRegistryData memberRegistryData);


    /**
     * 完成注册后
     * <p>
     * 完成所有注册工作，并提交到数据库后
     *
     * @param memberRegistryData
     */
    void afterCommitRegistry(MemberRegistryData memberRegistryData);

    /**
     * 注册异常时
     *
     * @param memberRegistryData
     */
    void exceptionRegistry(MemberRegistryData memberRegistryData, BusinessException be);
}
```

#### 2.4.2. 短信和邮件发送扩展

组件考虑，在特殊情况下，内置的短信或邮件模板及模板参数并不能满足集成系统需求的情况下，可以采用扩展的方式实现。组件发送信息模块设计在发送消息前，调用了拦截器进行处理，你可以实现拦截器（放入你的spring容器即可），返回你的模板名称和模板参数。

com.acooly.module.member.service.interceptor.MemberCaptchaInterceptor

```java
public interface MemberCaptchaInterceptor {


    /**
     * 根据配置发送注册短信验证码时的模板数据扩展。
     * <p>
     * 默认组件以在数据中提供：username, captcha
     * 如果你的注册短信验证码模板新增了其他数据，则请实现该方法扩展.
     * 注册短信的模板配置：acooly.member.active.smsTemplateContent
     *
     * @param member   被发送的用户
     * @param busiType 发送的业务（注册，重置密码等）
     * @param data     模板数据（需要回填）
     * @return 模板名称
     */
    String onCaptchaSMS(Member member, String busiType, Map<String, Object> data);


    MailSendInfo onCaptchaMail(Member member, String busiType, Map<String, Object> data);

}
```

#### 2.4.1. 会员自定义业务分类

会员组件内部已按主体类型划分了用户类型（个人，个体，企业），但实际业务场景中会有自定义业务分类的需求，这里提供已接口方式扩展自定义业务分类的能力。

**关键接口：**

* MemberBusiType：定义业务类型（核心为：code和name）
* MemberBusiTypeLoader：业务类型加载接口（核心为：提供列表返回的MemberBusiType）

**扩展方法：**

1. 在集成系统内实现MemberBusiType接口的类，作为业务分类的实体（也可以直接使用组件内的`DefaultMemberBusiType`默认实现）
2. 在集成系统内实现MemberBusiTypeLoader接口的`loadMemberBusiTypes`方法，返回你自定义的业务分类列表。
3. 把你实现的MemberBusiTypeLoader对象加入到spring容器中（`@Component`标记即可）

>完成后，你启动系统，可以在后头的会员管理模块的界面中直接选择你扩展的自定义业务类型，当然你也可以在后头的服务接口中解析使用。


### Q&A

#### 会员Path的应用及逻辑

path值在会员组件中的设计并未使用以前项目常用的方式：以自己的标志（ID或编码可能是生成的max）作为path的关键要素。而是采用目录的方式，每个会员的path相当于他说在的目录，也就是说是他父节点的路径。

* 顶层会员的path为: /
* 其他各层的path为: parentPath + parentId + /

例如：

			      			 |---> user12(id=12,path=/1/)
		user1(id=1,path=/)-->|
							 |                               |---> user111 (id=111,path=/1/11/)
							 |---> user11(id=11,path=/1/) -->|
							                                 |---> user112 (id=111,path=/1/11/)



* 搜索本层: path = user.path 
* 搜索所有子：path like user.path%
* 搜索相关的某层：根据/分割user.path进行处理。


