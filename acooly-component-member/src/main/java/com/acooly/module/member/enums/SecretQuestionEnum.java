/*
 * www.acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2018-08-03 18:43 创建
 */
package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 安全问题
 *
 * @author zhangpu 2018-08-03 18:43
 */
public enum SecretQuestionEnum implements Messageable {

    MATHER_BIRTHDAY("MATHER_BIRTHDAY", "我母亲的生日？"),

    FATHER_BIRTHDAY("FATHER_BIRTHDAY", "我父亲的生日？"),

    PRIMARY_SCHOOL_FULL_NAME("PRIMARY_SCHOOL_FULL_NAME", "我小学的全名称？"),

    MY_FAVORITE_MOVIE("MY_FAVORITE_MOVIE", "我最喜欢的电影？"),

    MY_GREATEST_HOBBY("MY_GREATEST_HOBBY", "我最大的爱好？"),

    MY_IDEAL_JOB("MY_IDEAL_JOB", "我的理想工作？"),

    MY_CHILDHOOD_NICKNAME("MY_CHILDHOOD_NICKNAME", "我童年时代的绰号？"),

    MY_FAVORITE_STAR_STUDENT("MY_FAVORITE_STAR_STUDENT", "我学生时代最喜欢的明星或角色？"),

    MY_FIRST_LEADER_NAME("MY_FIRST_LEADER_NAME", "我的第一个上司的名字？"),

    MY_BEST_FRIEND_BOYHOOD("MY_BEST_FRIEND_BOYHOOD", "我少年时代最好的朋友叫什么名字？");

    private final String code;
    private final String message;

    private SecretQuestionEnum(String code, String message) {
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
        for (SecretQuestionEnum type : values()) {
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
    public static SecretQuestionEnum find(String code) {
        for (SecretQuestionEnum status : values()) {
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
    public static List<SecretQuestionEnum> getAll() {
        List<SecretQuestionEnum> list = new ArrayList<SecretQuestionEnum>();
        for (SecretQuestionEnum status : values()) {
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
        for (SecretQuestionEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }


}
