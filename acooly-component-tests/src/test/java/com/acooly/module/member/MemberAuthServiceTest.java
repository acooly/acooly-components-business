package com.acooly.module.member;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberAuthInfo;
import com.acooly.module.member.dto.MemberInfo;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.AuthStatusEnum;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.error.MemberErrorEnum;
import com.acooly.module.member.exception.MemberOperationException;
import com.acooly.module.member.service.MemberAuthService;
import com.acooly.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 操作员安全相关服务
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 16:19
 */
@Slf4j
public class MemberAuthServiceTest extends AbstractComponentsTest {
    /**
     * 测试用户
     */
    static final String TEST_USERNAME = "zhangpu";
    static final String TEST_PASSWORD = "Ab123456";

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberAuthService memberAuthService;


    @Before
    public void before() {
        cleanMemberDatabase(TEST_USERNAME);
        log.info("初始化数据完成。username:{}", TEST_USERNAME);
    }


    /**
     * 新增操作员
     */
    @Test
    public void testAddOperator() {
        // 1、注册激活
        Member member = doRegister(true);
        MemberAuthInfo memberAuthInfo = new MemberAuthInfo();
        memberAuthInfo.setUserId(member.getId());
        memberAuthInfo.setUserNo(member.getUserNo());
        memberAuthInfo.setUsername(member.getUsername());
        memberAuthInfo.setLoginId(member.getUsername());
        memberAuthInfo.setLoginKey(TEST_PASSWORD);
        memberAuthService.create(memberAuthInfo);
    }


    /**
     * 测试会员认证
     */
    @Test
    public void testLogin() {
        // 1、注册+创建账号
        testAddOperator();
        // 2、登录认证: 成功
        memberAuthService.auth(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, TEST_PASSWORD);
        // 3、登录认证: 用户不存在
        try {
            memberAuthService.auth(MemberInfo.of(TEST_USERNAME), "1231232", TEST_PASSWORD);
        } catch (BusinessException be) {
            Assert.assertEquals(be.getCode(), MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST.code());
        }

    }

    @Test
    public void testLoginWithManualStatus() {
        // 1、注册+创建账号
        testAddOperator();
        // 2、登录认证: 成功
        memberAuthService.auth(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, TEST_PASSWORD);
        // 3、登录认证: 用户不存在
        try {
            memberAuthService.auth(MemberInfo.of(TEST_USERNAME), "1231232", TEST_PASSWORD);
        } catch (BusinessException be) {
            Assert.assertEquals(be.getCode(), MemberErrorEnum.AUTH_OPERATOR_NOT_EXIST.code());
        }

    }

    @Test
    public void testStatus() {
        // 1、注册+创建账号
        testAddOperator();
        // 状态修改：直接修改为密码过期和锁定 -> 不支持异常
        try {
            memberAuthService.updateStatus(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, AuthStatusEnum.invalid_expired);
        } catch (BusinessException be) {
            Assert.assertEquals(be.getCode(), MemberErrorEnum.AUTH_UNSUPPORT_OPERATE.code());
        }
        // 状态修改：禁用
        memberAuthService.updateStatus(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, AuthStatusEnum.invalid_disabled);

        try {
            memberAuthService.auth(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, TEST_PASSWORD);
        } catch (BusinessException be) {
            Assert.assertEquals(be.getCode(), MemberErrorEnum.AUTH_STATUS_NOT_ENABLE.code());
        }

        // 状态修改：启用
        memberAuthService.updateStatus(MemberInfo.of(TEST_USERNAME), TEST_USERNAME, AuthStatusEnum.enable);
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
//            memberSecurityService.login(TEST_USERNAME, TEST_PASSWORD);
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
//        memberSendingService.send(TEST_USERNAME, MemberTemplateEnum.changePassword, SendTypeEnum.SMS, true);
    }


    @Test
    public void testChangePasswordVerify() {
        // 1、验证验证码
//        memberSendingService.captchaVerify(TEST_USERNAME, MemberTemplateEnum.changePassword, SendTypeEnum.SMS, "racerw");
        // 2、验证老密码和修改密码
        String newPassword = "CD123456";
//        memberSecurityService.changePassword(TEST_USERNAME, TEST_PASSWORD, newPassword);
        // 3、验证新密码
//        memberSecurityService.login(TEST_USERNAME, newPassword);
    }


    protected Member doRegister(boolean actived) {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        if (actived) {
            memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        }
        return memberService.register(memberRegistryInfo);
    }

}
