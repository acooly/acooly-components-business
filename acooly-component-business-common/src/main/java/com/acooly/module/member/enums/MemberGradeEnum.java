package com.acooly.module.member.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-23 00:12
 */
public enum MemberGradeEnum {


    normal(0, "普通"),

    VIP1(1, "VIP1"),

    VIP2(2, "VIP2"),

    VIP3(3, "VIP3"),

    VIP4(4, "VIP4"),

    VIP5(5, "VIP5"),

    VIP6(6, "VIP6"),

    VIP7(7, "VIP7"),

    VIP8(8, "VIP8"),

    VIP9(9, "VIP9");


    private final int code;
    private final String message;

    private MemberGradeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public int code() {
        return code;
    }


    public String message() {
        return message;
    }


    public static Map<Integer, String> mapping() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (MemberGradeEnum type : values()) {
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
    public static MemberGradeEnum find(Integer code) {
        for (MemberGradeEnum status : values()) {
            if (status.getCode() == code) {
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
    public static List<MemberGradeEnum> getAll() {
        List<MemberGradeEnum> list = new ArrayList<MemberGradeEnum>();
        for (MemberGradeEnum status : values()) {
            list.add(status);
        }
        return list;
    }

    /**
     * 获取全部枚举值码。
     *
     * @return 全部枚举值码。
     */
    public static List<Integer> getAllCode() {
        List<Integer> list = new ArrayList<Integer>();
        for (MemberGradeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
