package com.acooly.module.member;

import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
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
public class MemberRegistryAndActiveTest extends AbstractComponentsTest {

    static final String TEST_USERNAME = "zhangpu";

    @Autowired
    private MemberService memberService;

    private String temporaryCaptcha = null;

    @Before
    public void before() {
        jdbcTemplate.execute("delete from b_member where username = '" + TEST_USERNAME + "'");
        jdbcTemplate.execute("delete from b_member_profile where username = '" + TEST_USERNAME + "'");
        jdbcTemplate.execute("delete from b_member_contact where username = '" + TEST_USERNAME + "'");
        jdbcTemplate.execute("delete from b_member_personal where username = '" + TEST_USERNAME + "'");
        jdbcTemplate.execute("delete from ac_account where username = '" + TEST_USERNAME + "'");
    }


    /**
     * 注册待手机验证码激活
     */
    @Test
    public void testRegisterActiveWithMobile() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername(TEST_USERNAME);
        memberRegistryInfo.setPassword("Ab123456");
        memberRegistryInfo.setMobileNo("13896177630");
        memberRegistryInfo.setRealName("秦海贤");
        memberRegistryInfo.setIdCardNo("360822198609284091");
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
        memberRegistryInfo.setPassword("Ab123456");
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

}
