package com.acooly.module.member.error;

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


    PARAMETER_ERROR("PARAMETER_ERROR", "参数错误"),

    MEMBER_PARAMS_ERROR("MEMBER_PARAMS_ERROR", "会员参数错误"),

    MEMBER_INTERNAL_ERROR("MEMBER_INTERNAL_ERROR", "账务内部错误"),

    MEMBER_REGISTRY_ARGUMENT_NOT_MATCH("MEMBER_REGISTRY_ARGUMENT_NOT_MATCH", "注册参数不匹配"),

    MEMEBER_USERNAME_ALREADY_EXIST("MEMEBER_USERNAME_ALREADY_EXIST", "会员用户名已存在"),

    MEMEBER_USERNO_ALREADY_EXIST("MEMEBER_USERNO_ALREADY_EXIST", "会员用户编码已存在"),

    MEMEBER_NOT_EXIST("MEMEBER_NOT_EXIST", "会员不存在"),

    BROKER_MUST_BE_A_MEMBER("BROKER_MUST_BE_A_MEMBER", "经纪人必须是会员"),

    INVITER_MUST_BE_A_MEMBER("INVITER_MUST_BE_A_MEMBER", "邀请人必须是会员"),

    MEMEBER_STATUS_NOT_ENABLE("MEMEBER_STATUS_NOT_ENABLE", "用户状态不可用"),


    MEMEBER_REALNAME_DATA_MISSING("MEMEBER_REALNAME_DATA_MISSING", "实名认证数据不全"),

    MEMEBER_REALNAME_FAIL("MEMEBER_REALNAME_FAIL", "实名认证未通过"),

    /**
     * 为安全级别，对外登录错误统一为一个错误消息。内部采用不同的日志区分
     */
    LOGIN_VERIFY_FAIL("LOGIN_VERIFY_FAIL", "用户名或密码错误"),

    LOGIN_PASSWORD_VERIFY_FAIL("LOGIN_PASSWORD_VERIFY_FAIL", "密码错误"),

    CAPTCHA_SEND_TARGET_EMPTY("CAPTCHA_SEND_TARGET_EMPTY", "验证码发送地址为空"),

    CAPTCHA_SEND_ERROR("CAPTCHA_SEND_ERROR", "验证码发送失败"),

    CAPTCHA_VERIFY_ERROR("CAPTCHA_SEND_ERROR", "验证码验证失败"),


    AUTH_ADMIN_NOT_UNIQUE("AUTH_ADMIN_OPT_NOT_UNIQUE", "会员主账号不唯一"),

    AUTH_LOGINID_NOT_UNIQUE("AUTH_LOGINID_NOT_UNIQUE", "会员子账号登录名不唯一"),

    AUTH_OPERATOR_NOT_EXIST("AUTH_OPERATOR_NOT_EXIST", "账号不存在"),

    AUTH_STATUS_NOT_ENABLE("AUTH_STATUS_NOT_ENABLE", "账号状态不可用"),

    AUTH_KEY_EXPIRED("AUTH_KEY_EXPIRED", "账号密码/Key已过期"),

    AUTH_OPERATOR_LOCKED("AUTH_OPERATOR_LOCKED", "账号已锁定"),

    AUTH_UNSUPPORT_OPERATE("AUTH_UNSUPPORT_OPERATE", "不支持的操作"),

    AUTH_VERIFY_FAIL("AUTH_VERIFY_FAIL", "用户名或密码错误"),;

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
