/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-25 16:19 创建
 */
package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu 2018-07-25 16:19
 */
public enum GenderEnum implements Messageable {

    male("male", "男"),
    female("female", "女");

    private final String code;
    private final String message;

    private GenderEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = Maps.newLinkedHashMap();
        for (GenderEnum type : values()) {
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
    public static GenderEnum find(String code) {
        for (GenderEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }

        if (GenderEnum.male.message.equals(code)) {
            return GenderEnum.male;
        }
        if (GenderEnum.female.message.equals(code)) {
            return GenderEnum.female;
        }

        throw null;
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<GenderEnum> getAll() {
        List<GenderEnum> list = new ArrayList<GenderEnum>();
        for (GenderEnum status : values()) {
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
        for (GenderEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.code, this.message);
    }

}
