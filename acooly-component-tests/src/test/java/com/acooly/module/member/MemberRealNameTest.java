package com.acooly.module.member;

import com.acooly.module.AbstractComponentsTest;
import com.acooly.module.member.dto.MemberRealNameInfo;
import com.acooly.module.member.dto.MemberRegistryInfo;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.MemberRealNameService;
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

    @Autowired
    private MemberRealNameService memberRealNameService;

    @Before
    public void before() {
        //cleanMemberDatabase(TEST_USERNAME);
    }


    /**
     * 注册个人会员自动实名
     * <p>
     * 需要打开member组件的配置参数：realNameOnRegistry=true
     * 或者
     * 使用下面的构造，自动设置 MemberRegistryInfo的realNameOnRegistry=true
     */
    @Test
    public void testRegisterAndAutoPersonalRealName() {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo("aaa", "Ab123456", "13896177638",
                "王琳嵛", "500225199009264473");
        Member member = memberService.register(memberRegistryInfo);
        log.info("注册成功。member:{}", member);
    }

    /**
     * 注册后再实名
     * 1、注册个人会员不实名
     * 2、调用独立的实名认证
     */
    @Test
    public void testRegisterAndPersonalRealName() {
        // 注册
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, "Ab123456", "13896177630",
                null, null);
        Member member = memberService.register(memberRegistryInfo);
        //实名
        memberRealNameService.verify(new MemberRealNameInfo(member.getId(), "王琳嵛", "500225199009264473"));
    }


    /**
     * 注册后外部实名成功后回谢实名
     * 1、注册个人会员不实名
     * 2、外部实名（支付绑卡接口实名完成）成功后，会写到会员组件
     */
    @Test
    public void testRegisterAndOutPersonalRealName() {
        // 注册
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, "Ab123456", "13896177630",
                null, null);
        Member member = memberService.register(memberRegistryInfo);
        //实名(不调用实名组件验证，只对身份证解析)
        memberRealNameService.saveVerify(new MemberRealNameInfo(member.getId(), "秦海贤", "360822198609284091"));
    }
}
