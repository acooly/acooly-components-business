package com.acooly.module.member;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.enums.MemberActiveTypeEnum;
import com.acooly.module.member.service.MemberService;
import com.acooly.module.test.AppTestBase;
import com.acooly.module.test.Main;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 18:19
 */
@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Slf4j
public class MemberServiceTest extends AppTestBase {

    public static final String PROFILE = "sdev";

    //设置环境
    static {
        Apps.setProfileIfNotExists(PROFILE);
    }

    @Autowired
    private MemberService memberService;

    /**
     * 最简：测试注册
     */
    @Test
    public void testRegister() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername("zhangpu");
        memberRegistryInfo.setPassword("Ab123456");
        memberService.register(memberRegistryInfo);
    }

    /**
     * 注册
     * 1、同步注册账户
     * 2、自动激活
     */
    @Test
    public void testRegisterMix() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername("zhangpu8");
        memberRegistryInfo.setPassword("Ab123456");
        memberRegistryInfo.setMobileNo("13896177630");
        memberRegistryInfo.setAccountRegisty(true);
        memberService.register(memberRegistryInfo);
    }


    /**
     * 注册待手机验证码激活
     */
    @Test
    public void testRegisterActiveWithMobile() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername("zhangpu10");
        memberRegistryInfo.setPassword("Ab123456");
        memberRegistryInfo.setMobileNo("13896177630");
        memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.mobileNo);
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 使用手机验证码激活注册
     * 需要启动独立的redis服务
     */
    @Test
    public void testActiveWithMobile() {
        memberService.active("zhangpu10", "fhan24");
    }

}
