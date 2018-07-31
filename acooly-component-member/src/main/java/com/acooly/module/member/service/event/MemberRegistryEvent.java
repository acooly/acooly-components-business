package com.acooly.module.member.service.event;

import com.acooly.module.member.service.interceptor.MemberRegistryData;

/**
 * 注册事件
 * <p>
 * 该事件主要用于注册成功后(commit后)，可扩展实现其他业务功能，不同interceptor，事件默认可以有多个扩展并容易解耦
 *
 * @author zhangpu@acooly.cn
 * @date 2018-07-29 00:39
 */
public class MemberRegistryEvent extends MemberRegistryData {

}
