package com.acooly.module.member;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.member.dto.MemberRegistryInfo;
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
     */
    @Test
    public void testRegisterMix() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo();
        memberRegistryInfo.setUsername("zhangpu");
        memberRegistryInfo.setPassword("Ab123456");
        memberService.register(memberRegistryInfo);
    }


}
