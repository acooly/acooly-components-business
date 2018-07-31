package com.acooly.module.member.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-23 00:12
 */
public enum MemberUserTypeEnum {


    personal("personal", "个人"),

    enterprise_indi("enterprise_indi", "个体"),

    enterprise("enterprise", "企业");


    private final String code;
    private final String message;

    private MemberUserTypeEnum(String code, String message) {
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
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (MemberUserTypeEnum type : values()) {
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
    public static MemberUserTypeEnum find(String code) {
        for (MemberUserTypeEnum status : values()) {
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
    public static List<MemberUserTypeEnum> getAll() {
        List<MemberUserTypeEnum> list = new ArrayList<MemberUserTypeEnum>();
        for (MemberUserTypeEnum status : values()) {
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
        for (MemberUserTypeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
