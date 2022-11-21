/*
 * acooly.cn Inc.
 * Copyright (c) 2021 All Rights Reserved.
 * create by zhangpu@acooly.cn
 * date:2021-08-12
 *
 */
package cn.acooly.component.assetmgmt.enums;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceTypeEnum 枚举定义
 *
 * @author zhangpu@acooly.cn
 * @date 2021-08-12 22:44:09
 */
public enum ServiceTypeEnum implements Messageable {

    ssh("ssh", "远程登录"),
    database("database", "数据库"),
    email("email", "邮件"),
    web("web", "Web服务"),
    redis("redis", "redis"),
    other("other", "其他");

    private final String code;
    private final String message;

    private ServiceTypeEnum(String code, String message) {
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
        for (ServiceTypeEnum type : values()) {
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
    public static ServiceTypeEnum find(String code) {
        for (ServiceTypeEnum status : values()) {
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
    public static List<ServiceTypeEnum> getAll() {
        List<ServiceTypeEnum> list = new ArrayList<ServiceTypeEnum>();
        for (ServiceTypeEnum status : values()) {
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
        for (ServiceTypeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
