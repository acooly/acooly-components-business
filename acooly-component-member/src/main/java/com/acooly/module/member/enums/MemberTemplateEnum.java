package com.acooly.module.member.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员相关的业务操作规范名称
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-05 21:42
 */
public enum MemberTemplateEnum implements Messageable {

    common("common", "通用"),

    register("register", "注册"),

    active("active", "激活"),

    changePassword("changePassword", "修改密码"),

    changePasswordSuccess("changePasswordSuccess", "修改密码成功"),

    RESET_PASSWORD("RESET_PASSWORD", "重置密码"),

    RESET_PASSWORD_SUCCESS("RESET_PASSWORD_SUCCESS", "重置密码成功"),

    CHANGE_MOBILE_NO("CHANGE_MOBILE_NO", "修改手机号码"),

    CHANGE_MOBILE_NO_SUCCESS("CHANGE_MOBILE_NO_SUCCESS", "修改手机号码成功");


    private final String code;
    private final String message;

    private MemberTemplateEnum(String code, String message) {
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
        for (MemberTemplateEnum type : values()) {
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
    public static MemberTemplateEnum find(String code) {
        for (MemberTemplateEnum status : values()) {
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
    public static List<MemberTemplateEnum> getAll() {
        List<MemberTemplateEnum> list = new ArrayList<MemberTemplateEnum>();
        for (MemberTemplateEnum status : values()) {
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
        for (MemberTemplateEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
