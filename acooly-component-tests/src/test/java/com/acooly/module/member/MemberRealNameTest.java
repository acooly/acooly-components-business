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
 * 测试会员实名认证
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-01 12:19
 */
@Slf4j
public class MemberRealNameTest extends AbstractComponentsTest {

    static final String TEST_USERNAME = "zhangpu";

    @Autowired
    private MemberService memberService;

    @Before
    public void before() {
        cleanDatabase(TEST_USERNAME);
    }


    /**
     * 注册个人会员自动实名
     * <p>
     * 需要打开member组件的配置参数：realNameOnRegistry=true
     * 或者
     * 使用下面的构造，自动设置 MemberRegistryInfo的realNameOnRegistry=true
     */
    @Test
    public void testRegisterAndAutoRealName() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, "Ab123456", "13896177630",
                "秦海贤", "360822198609284091");
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 注册个人会员不实名
     */
    @Test
    public void testRegister() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, "Ab123456", "13896177630",
                "秦海贤", "360822198609284091");
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
