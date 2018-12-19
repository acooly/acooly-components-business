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
 * 操作员登录认证测试
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-06 16:19
 */
@Slf4j
public class MemberAuthLoginTest extends AbstractComponentsTest {
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
//        cleanMemberDatabase(TEST_USERNAME);
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


    @Test
    public void testLogin() {
        memberAuthService.auth(MemberInfo.of(TEST_USERNAME),"1111");
    }


    protected Member doRegister(boolean actived) {
        MemberRegistryInfo memberRegistryInfo = new MemberRegistryInfo(TEST_USERNAME, TEST_PASSWORD, "13896177630");
        if (actived) {
            memberRegistryInfo.setMemberActiveType(MemberActiveTypeEnum.auto);
        }
        return memberService.register(memberRegistryInfo);
    }


}
