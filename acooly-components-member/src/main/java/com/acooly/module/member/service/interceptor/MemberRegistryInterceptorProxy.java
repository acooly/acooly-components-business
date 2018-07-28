package com.acooly.module.member.service.interceptor;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.integration.bean.AbstractSpringProxyBean;
import com.acooly.module.member.entity.Member;
import com.acooly.module.member.service.interceptor.impl.EmptyMemberRegistryInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代理获取集成系统的MemberRegistryInterceptor实现
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-28 23:38
 */
@Component("memberRegistryInterceptor")
public class MemberRegistryInterceptorProxy extends AbstractSpringProxyBean<MemberRegistryInterceptor, EmptyMemberRegistryInterceptor> implements MemberRegistryInterceptor {


    @Override
    public void beginRegistry(MemberRegistryData memberRegistryData) {
        getTarget().beginRegistry(memberRegistryData);
    }

    @Override
    public void endRegistry(MemberRegistryData memberRegistryData) {
        getTarget().endRegistry(memberRegistryData);
    }

    @Override
    public void afterCommitRegistry(MemberRegistryData memberRegistryData) {
        getTarget().afterCommitRegistry(memberRegistryData);
    }

    @Override
    public void exceptionRegistry(MemberRegistryData memberRegistryData, BusinessException be) {
        getTarget().exceptionRegistry(memberRegistryData, be);
    }

    @Override
    public void onCaptchaSms(Member member, Map<String, Object> data) {
        getTarget().onCaptchaSms(member, data);
    }

    @Override
    public void onCaptchaMail(Member member, Map<String, String> data) {
        getTarget().onCaptchaMail(member, data);
    }
}
