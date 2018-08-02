package com.acooly.module.member;

import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
public class MemberServiceTest extends AbstractComponentsTest {
    /**
     * 测试用户
     */
    static final String TEST_USERNAME = "zhangpu";
    static final String TEST_PASSWORD = "Ab123456";

    /**
     * 测试的经纪人用户
     */
    static final String TEST_BROKER_USERNAME = "broker";

    @Autowired
    private MemberService memberService;


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
        memberRegistryInfo.setAccountRegisty(true);

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
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 注册待邮件验证码激活
     */
    @Test
    public void testRegisterActiveWithMail() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername(TEST_USERNAME);
        memberRegistryInfo.setPassword(TEST_PASSWORD);
        memberRegistryInfo.setEmail("zhangpu@acooly.cn");
        memberRegistryInfo.setAccountRegisty(false);
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.email);
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 使用手机验证码激活注册
     * 需要启动独立的redis服务
     */
    @Test
    public void testActiveWithCaptcha() {
        memberService.active(TEST_USERNAME, "kpa3bd", MemberActiveTypeEnum.email);
    }


    /**
     * 测试会员认证
     */
    @Test
    public void testLogin() {
        // 1、注册激活
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        Member member = memberService.register(memberRegistryInfo);
        // 2、登录认证
        memberService.login(member.getUsername(), TEST_PASSWORD);
    }

    /**
     * 测试为激活会员认证
     */
    @Test
    public void testLoginNoActive() {

        // 1、注册不激活
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        Member member = memberService.register(memberRegistryInfo);
        // 2、登录认证
        try {
            memberService.login(TEST_USERNAME, TEST_PASSWORD);
        } catch (MemberOperationException e) {
            Assert.assertEquals(e.code(), MemberErrorEnum.LOGIN_VERIFY_FAIL.code());
        }

    }


}
