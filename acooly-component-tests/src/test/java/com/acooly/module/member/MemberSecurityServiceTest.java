package com.acooly.module.member;

import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.enums.MemberTemplateEnum;
import com.acooly.module.member.enums.SendTypeEnum;
import com.acooly.module.member.error.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.MemberSecurityService;
import com.acooly.module.member.service.MemberSendingService;
import com.acooly.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 会员安全相关服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 16:19
 */
@Slf4j
public class MemberSecurityServiceTest extends AbstractMemberTest {
    /**
     * 测试用户
     */
    static final String TEST_USERNAME = "zhangpu";
    static final String TEST_PASSWORD = "Ab123456";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSecurityService memberSecurityService;

    @Autowired
    private MemberSendingService memberSendingService;


    @Before
    public void before() {
        cleanMemberDatabase(TEST_USERNAME);
        log.info("初始化数据完成。username:{}", TEST_USERNAME);
    }


    /**
     * 测试会员认证
     */
    @Test
    public void testLogin() {
        // 1、注册激活
        doRegister(true);
        // 2、登录认证
        memberSecurityService.login(TEST_USERNAME, TEST_PASSWORD);
    }

    /**
     * 测试为激活会员认证
     */
    @Test
    public void testLoginNoActive() {

        // 1、注册不激活
        doRegister(false);
        // 2、登录认证
        try {
            memberSecurityService.login(TEST_USERNAME, TEST_PASSWORD);
        } catch (MemberOperationException e) {
            Assert.assertEquals(e.code(), MemberErrorEnum.LOGIN_VERIFY_FAIL.code());
        }
    }


    /**
     * 测试修改密码
     * <p>
     * 假设逻辑为：先通过短信验证，然后再修改密码
     *
     * <p>
     * 第一步：发送验证短信
     */
    @Test
    public void testChangePasswordSend() {
        doRegister(true);
        memberSendingService.send(TEST_USERNAME, MemberTemplateEnum.changePassword, SendTypeEnum.SMS, true);
    }


    @Test
    public void testChangePasswordVerify() {
        // 1、验证验证码
        memberSendingService.captchaVerify(TEST_USERNAME, MemberTemplateEnum.changePassword, SendTypeEnum.SMS, "racerw");
        // 2、验证老密码和修改密码
        String newPassword = "CD123456";
        memberSecurityService.changePassword(TEST_USERNAME, TEST_PASSWORD, newPassword);
        // 3、验证新密码
        memberSecurityService.login(TEST_USERNAME, newPassword);
    }


    protected void doRegister(boolean actived) {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        if (actived) {
            memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        }
        memberService.register(memberRegistryInfo);
    }

}
