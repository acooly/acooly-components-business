/**
 * acooly-components-business-5.2
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-11-20 11:33
 */
package cn.acooly.component.assetmgmt.enums;
/**
 * 资产状态
 *
 * @author zhangpu
 * @date 2022-11-20 11:33
 */

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum SecretStatus implements Messageable {
    enable("enable", "正常"),
    expired("expired", "过期"),
    disable("disable", "禁用");

    private final String code;
    private final String message;

    SecretStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (SecretStatus type : values()) {
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
    public static SecretStatus find(String code) {
        for (SecretStatus status : values()) {
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
    public static List<SecretStatus> getAll() {
        List<SecretStatus> list = new ArrayList<SecretStatus>();
        for (SecretStatus status : values()) {
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
        for (SecretStatus status : values()) {
            list.add(status.code());
        }
        return list;
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

}
