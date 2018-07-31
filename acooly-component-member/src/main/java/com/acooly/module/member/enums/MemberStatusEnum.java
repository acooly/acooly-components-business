/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-07-23
 *
 */
package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员信息 MemberStatusEnum 枚举定义
 *
 * @author acooly
 * Date: 2018-07-23 00:05:26
 */
public enum MemberStatusEnum implements Messageable {

    notactive("notactive", "未激活"),

    enable("enable", "正常"),

    enable_lock("enable_lock", "锁定"),

    disable("disable", "禁用"),;

    private final String code;
    private final String message;

    private MemberStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (MemberStatusEnum type : values()) {
            map.put(type.getCode(), type.getMessage());
        }
        return map;
    }

    /**
     * 通过枚举值码查找枚举值。
     *
     * @param code 查找枚举值的枚举值码。
     * @return 枚举值码对应的枚举值。
     * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
     */
    public static MemberStatusEnum find(String code) {
        for (MemberStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<MemberStatusEnum> getAll() {
        List<MemberStatusEnum> list = new ArrayList<MemberStatusEnum>();
        for (MemberStatusEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<String> getAllCode() {
        List<String> list = new ArrayList<String>();
        for (MemberStatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
