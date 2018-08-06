package com.acooly.module.member;

import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.exception.MemberErrorEnum;
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
public class MemberSecurityServiceTest extends AbstractComponentsTest {
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
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        Member member = memberService.register(memberRegistryInfo);
        // 2、登录认证
        memberSecurityService.login(member.getUsername(), TEST_PASSWORD);
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
            memberSecurityService.login(TEST_USERNAME, TEST_PASSWORD);
        } catch (MemberOperationException e) {
            Assert.assertEquals(e.code(), MemberErrorEnum.LOGIN_VERIFY_FAIL.code());
        }

    }


    /**
     * 测试修改密码
     * <p>
     * 第一步：发送验证短信
     */
    @Test
    public void testChangePasswordSend() {

    }


    public void testChangePasswordVerify() {

    }


}
