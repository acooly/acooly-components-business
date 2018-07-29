package com.acooly.module.member.exception;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 16:20
 */
public enum MemberErrorEnum implements Messageable {


    MEMBER_INTERNAL_ERROR("MEMBER_INTERNAL_ERROR", "账务内部错误"),

    MEMBER_REGISTRY_ARGUMENT_NOT_MATCH("MEMBER_REGISTRY_ARGUMENT_NOT_MATCH", "注册参数不匹配"),

    MEMEBER_USERNAME_ALREADY_EXIST("MEMEBER_USERNAME_ALREADY_EXIST", "会员用户名已存在"),

    MEMEBER_USERNO_ALREADY_EXIST("MEMEBER_USERNO_ALREADY_EXIST", "会员用户编码已存在"),

    MEMEBER_NOT_EXIST("MEMEBER_NOT_EXIST", "会员不存在"),

    BROKER_MUST_BE_A_MEMBER("BROKER_MUST_BE_A_MEMBER", "经纪人必须是会员"),

    INVITER_MUST_BE_A_MEMBER("INVITER_MUST_BE_A_MEMBER", "邀请人必须是会员"),


    MEMEBER_REALNAME_DATA_MISSING("MEMEBER_REALNAME_DATA_MISSING", "实名认证数据不全"),

    MEMEBER_REALNAME_FAIL("MEMEBER_REALNAME_FAIL", "实名认证未通过"),

    LOGIN_PASSWORD_VERIFY_FAIL("LOGIN_PASSWORD_VERIFY_FAIL", "密码错误"),;


    private final String code;
    private final String message;

    private MemberErrorEnum(String code, String message) {
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

    public String getLabel() {
        return this.code + "/" + this.message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (MemberErrorEnum type : values()) {
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
    public static MemberErrorEnum find(String code) {
        for (MemberErrorEnum status : values()) {
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
    public static List<MemberErrorEnum> getAll() {
        List<MemberErrorEnum> list = new ArrayList<MemberErrorEnum>();
        for (MemberErrorEnum status : values()) {
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
        for (MemberErrorEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
