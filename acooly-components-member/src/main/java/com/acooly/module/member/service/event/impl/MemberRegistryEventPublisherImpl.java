/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-26 18:05 创建
 */
package com.acooly.module.member.service.event.impl;

import com.acooly.module.event.EventBus;
import com.acooly.module.member.service.event.MemberRegistryEvent;
import com.acooly.module.member.service.event.MemberRegistryEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu 2018-07-26 18:05
 */
@Component
public class MemberRegistryEventPublisherImpl implements MemberRegistryEventPublisher {

    @Autowired
    private EventBus eventBus;

    @Override
    public void before(MemberRegistryEvent memberRegistryEvent) {
        eventBus.publish(memberRegistryEvent);
    }

    @Override
    public void begin(MemberRegistryEvent memberRegistryEvent) {

    }

    @Override
    public void end(MemberRegistryEvent memberRegistryEvent) {

    }

    @Override
    public void after(MemberRegistryEvent memberRegistryEvent) {

    }

    @Override
    public void exception(MemberRegistryEvent memberRegistryEvent) {

    }
}
