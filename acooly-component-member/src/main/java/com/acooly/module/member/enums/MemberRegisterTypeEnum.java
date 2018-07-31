/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-23 17:25 创建
 */
package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会员注册方式
 *
 * @author zhangpu 2018-07-23 17:25
 */
public enum MemberRegisterTypeEnum implements Messageable {


    normal("normal", "正常"),

    actived("actived", "激活");


    private final String code;
    private final String message;

    private MemberRegisterTypeEnum(String code, String message) {
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
        Map<String, String> map = Maps.newLinkedHashMap();
        for (MemberRegisterTypeEnum type : values()) {
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
    public static MemberRegisterTypeEnum find(String code) {
        for (MemberRegisterTypeEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("MemberRegisterTypeEnum not legal:" + code);
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<MemberRegisterTypeEnum> getAll() {
        List<MemberRegisterTypeEnum> list = new ArrayList<MemberRegisterTypeEnum>();
        for (MemberRegisterTypeEnum status : values()) {
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
        for (MemberRegisterTypeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.code, this.message);
    }

}
