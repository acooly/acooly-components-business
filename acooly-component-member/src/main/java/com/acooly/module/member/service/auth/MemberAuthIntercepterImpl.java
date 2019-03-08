package com.acooly.module.member.service.auth;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Collections3;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhangpu
 * @date 2018-10-30 16:23
 */
@Component
@Slf4j
public class MemberAuthIntercepterImpl implements MemberAuthIntercepter, InitializingBean {

    @Autowired
    protected ApplicationContext applicationContext;

    private List<MemberAuthIntercepter> memberAuthIntercepters = Lists.newArrayList();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            Map<String, MemberAuthIntercepter> targets = this.applicationContext.getBeansOfType(MemberAuthIntercepter.class);
            Iterator<MemberAuthIntercepter> iterator = targets.values().iterator();
            MemberAuthIntercepter memberAuthIntercepter;
            while (iterator.hasNext()) {
                memberAuthIntercepter = iterator.next();
                if (memberAuthIntercepter != this && !memberAuthIntercepter.getClass().equals(this.getClass())) {
                    memberAuthIntercepters.add(memberAuthIntercepter);
                }
            }
            interceptersSort();
            log.info("代理接口:{},实现: {}个: {}", MemberAuthIntercepter.class, memberAuthIntercepters.size(),
                    memberAuthIntercepters);
        } catch (Exception var4) {
            throw new RuntimeException("Spring容器自定义代理接口初始化失败", var4);
        }
    }

    private void interceptersSort() {
        if (Collections3.isEmpty(memberAuthIntercepters)) {
            return;
        }
        Ordering<MemberAuthIntercepter> ordering = Ordering.from(new Comparator<MemberAuthIntercepter>() {
            @Override
            public int compare(MemberAuthIntercepter o1, MemberAuthIntercepter o2) {
                return Ints.compare(o1.getOrder(), o2.getOrder());
            }
        });
        Collections.sort(memberAuthIntercepters, ordering);
    }


    @Override
    public void before(MemberAuthContext memberAuthContext) {
        for (MemberAuthIntercepter memberAuthIntercepter : memberAuthIntercepters) {
            memberAuthIntercepter.before(memberAuthContext);
        }
    }

    @Override
    public void success(MemberAuthContext memberAuthContext) {
        for (MemberAuthIntercepter memberAuthIntercepter : memberAuthIntercepters) {
            memberAuthIntercepter.success(memberAuthContext);
        }
    }

    @Override
    public void error(MemberAuthContext memberAuthContext, BusinessException e) {
        for (MemberAuthIntercepter memberAuthIntercepter : memberAuthIntercepters) {
            memberAuthIntercepter.error(memberAuthContext, e);
        }
    }

    @Override
    public void complete(MemberAuthContext memberAuthContext) {
        for (MemberAuthIntercepter memberAuthIntercepter : memberAuthIntercepters) {
            memberAuthIntercepter.complete(memberAuthContext);
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
