/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-08-02 14:21 创建
 */
package com.acooly.module.member.event;

import com.acooly.module.event.EventHandler;
import com.acooly.module.member.service.event.MemberRegistryEvent;
import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;

/**
 * @author zhangpu 2018-08-02 14:21
 */
@Slf4j
@EventHandler
public class TestMemberRegistryEventHandler {

    //同步事件处理器
    @Handler
    public void handleMemberRegistryEvent(MemberRegistryEvent event) {
        log.info("这里是注册成功后的事件处理扩展。MemberRegistryEvent:{}", event);
    }
}
