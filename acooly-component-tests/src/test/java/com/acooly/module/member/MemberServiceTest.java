package com.acooly.module.member;

import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberTemplateEnum;
import com.acooly.module.member.enums.MemberUserTypeEnum;
import com.acooly.module.member.enums.SendTypeEnum;
import com.acooly.module.member.service.MemberSendingService;
import com.acooly.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试注册和激活
 * <p>
 * 注意：本测试需要启动独立的redis，不能使用sdev环境自动启动的。
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@Slf4j
public class MemberServiceTest extends AbstractMemberTest {
    /**
     * 测试用户
     */
    static final String TEST_USERNAME = "user";
    static final String TEST_PASSWORD = "Ab123456";
    static final String TEST_MOBILE_NO = "13896177630";

    /**
     * 测试父用户
     */
    static final String TEST_PARENT_USERNAME = "user_parent";
    static final String TEST_CHILD_USERNAME = "user_child";
    /**
     * 测试的经纪人用户
     */
    static final String TEST_BROKER_USERNAME = "broker";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSendingService memberSendingService;


    @Before
    public void before() {
        cleanMemberDatabase(TEST_USERNAME);
        cleanMemberDatabase(TEST_BROKER_USERNAME);
        log.info("清理测试数据完成。username:{}", TEST_USERNAME);
        // 准备经纪人用户
        jdbcTemplate.execute("INSERT INTO `acooly`.`b_member`(`id`, `parentid`, `parent_user_no`, `user_no`, `username`, `password`, " +
                "`salt`, `mobile_no`, `email`, `real_name`, `cert_no`, `status`, `user_type`, `grade`, `create_time`, `update_time`, `comments`) " +
                "VALUES (100000, NULL, NULL, '1807261924130212121', '" + TEST_BROKER_USERNAME + "', '3922ae2b81e89556aed380f8ea2bcfc3dcb34609', 'c7db2da4b504ee69'," +
                " '13896177630', NULL, NULL, NULL, 'enable', 'personal', 0, '2018-07-26 19:24:13', '2018-07-26 19:27:10', NULL)");

        log.info("初始化数据完成。username:{}", TEST_BROKER_USERNAME);
    }


    /**
     * 完整注册
     * <p>
     * 1、激活：注册后自动激活（异步，可调整，或参考其他用例方法）
     * 2、实名：自动调用实名认证接口（同步）
     * 3、账户：自动开通默认资金账户
     * 4、营销：设置介绍人，经济人（设置为必须为会员），客户经理（sys_user）
     * 5、扩展：实现拦截，注册会员是注册会员扩展信息
     * 6、事件：注册完成后，订阅事件处理后续业务（比如发积分）
     */
    @Test
    public void testRegister() {
        // 2.实名：注册并并自动调用实名
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, "Ab123456", "13896177630",
                "秦海贤", "360822198609284091");
        // 1、激活，选择自动激活，手机或邮箱必填其一
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        // 3、账户：同步开户
        memberRegistryInfo.setAccountRegisty(false);

        // 4、营销
        // 设置客户经理（后台sys_user或其他任意ID或标志）
        memberRegistryInfo.setManager("admin");
        // 经济人：这里设置为必须为会员. 会员组件参数：acooly.member.brokerMustBeMember=true
        memberRegistryInfo.setBroker(TEST_BROKER_USERNAME);
        // 邀请/介绍人：配置为可以为任何业务层定义的标记。acooly.member.inviterMustBeMember=false
        memberRegistryInfo.setInviter("busiInviter");

        // 5、扩展 拦截实现注册业务层的 memberExtent实体（事务内）
        // 请参考：com.acooly.module.member.event.TestMemberRegistryInterceptor

        // 6、注册成功事件处理（单元测试因完成后shutdown容器，暂时无法模拟，请参考实现即可）
        // 请参考：com.acooly.module.member.event.TestMemberRegistryEventHandler

        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }


    /**
     * 注册待手机验证码激活
     */
    @Test
    public void testRegisterActiveWithMobile() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername(TEST_USERNAME);
        memberRegistryInfo.setPassword(TEST_PASSWORD);
        memberRegistryInfo.setMobileNo("13896177630");
        memberRegistryInfo.setRealName("秦海贤");
        memberRegistryInfo.setCertNo("360822198609284091");
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.mobileNo);
        memberRegistryInfo.setParentid(100057L);
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 注册待邮件验证码激活
     */
    @Test
    public void testRegisterActiveWithMail() {
        // 创建父会员
        MemberRegistryInfo parentRegistryInfo = new MemberRegistryInfo(TEST_PARENT_USERNAME, TEST_PASSWORD, TEST_MOBILE_NO);
        Member parent = memberService.register(parentRegistryInfo);

        // 创建会员 (通过parentId指定父节点)
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, TEST_MOBILE_NO);
        memberRegistryInfo.setParentid(parent.getId());
        Member member = memberService.register(memberRegistryInfo);

        // 创建子会员 (通过parentNo指定父节点)
        MemberRegistryInfo childRegistryInfo = new MemberRegistryInfo(TEST_CHILD_USERNAME, TEST_PASSWORD, TEST_MOBILE_NO);
        childRegistryInfo.setParentUserNo(member.getUserNo());
        Member child = memberService.register(childRegistryInfo);


    }

    /**
     * 注册待手机验证码激活
     */
    @Test
    public void testRegisterEnterpriseActiveWithMobile() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, TEST_MOBILE_NO);
        memberRegistryInfo.setMobileNo("13896177630");
        memberRegistryInfo.setRealName("普易软件（上海）有限公司重庆分公司");
        memberRegistryInfo.setCertNo("91500000MA5UHELY6Q");
        memberRegistryInfo.setMemberUserType(MemberUserTypeEnum.enterprise);
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.mobileNo);
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }


    /**
     * 注册父子关系
     */
    @Test
    public void testRegisterWithParent() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername(TEST_USERNAME);
        memberRegistryInfo.setPassword(TEST_PASSWORD);
        memberRegistryInfo.setEmail("zhangpu@acooly.cn");
        memberRegistryInfo.setRealName("秦海贤");
        memberRegistryInfo.setCertNo("360822198609284091");
        memberRegistryInfo.setAccountRegisty(false);
        memberRegistryInfo.setRealNameOnRegisty(true);
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.email);
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }


    /**
     * 测试再次发送（短信或邮件）
     */
    @Test
    public void testActiveSendWithSms() {
        memberService.activeSend(TEST_USERNAME, MemberActiveTypeEnum.email);
    }

    /**
     * 使用手机验证码激活注册
     * 需要启动独立的redis服务
     */
    @Test
    public void testActiveWithCaptcha() {
        memberService.active(TEST_USERNAME, "6225fh", MemberActiveTypeEnum.email);
    }


    /**
     * 先短信验证再注册场景（App短信注册，手机号码可以是用户名）
     * 第一步：发送短信验证码
     */
    @Test
    public void testAppRegisterCaptchaSend() {
        memberSendingService.send(TEST_USERNAME, TEST_MOBILE_NO, MemberTemplateEnum.registerQuick, SendTypeEnum.SMS, true);
    }

    /**
     * 先短信验证再注册场景
     * 第二部：验证短信验证码通过后直接注册为激活的用户
     */
    @Test
    public void testAppCaptchaVerifyAndRegistry() {
        // 1、验证短信验证码
        memberSendingService.captchaVerify(TEST_USERNAME, TEST_MOBILE_NO, MemberTemplateEnum.registerQuick, SendTypeEnum.SMS, "bcy47w");
        // 2、直接注册并激活用户
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, TEST_MOBILE_NO);
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        memberService.register(memberRegistryInfo);
    }


}
