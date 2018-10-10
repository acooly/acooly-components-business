/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-07-30 20:09 创建
 */
package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 认证状态
 *
 * @author zhangpu 2018-07-30 20:09
 */
public enum CertStatusEnum implements Messageable {


    yes("yes", "已认证"),
    no("no", "未认证"),
    no_expire("no_expire", "已过期");

    private final String code;
    private final String message;

    private CertStatusEnum(String code, String message) {
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
        for (CertStatusEnum type : values()) {
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
    public static CertStatusEnum find(String code) {
        for (CertStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw null;
    }

    /**
     * 获取全部枚举值。
     *
     * @return 全部枚举值。
     */
    public static List<CertStatusEnum> getAll() {
        List<CertStatusEnum> list = new ArrayList<CertStatusEnum>();
        for (CertStatusEnum status : values()) {
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
        for (CertStatusEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
