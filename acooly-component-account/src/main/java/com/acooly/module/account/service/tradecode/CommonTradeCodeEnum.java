package com.acooly.module.account.service.tradecode;

import com.acooly.module.account.enums.DirectionEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-12 15:27
 */
public enum CommonTradeCodeEnum implements TradeCode {


    deposit("deposit", "充值", DirectionEnum.in),

    deposit_offline("deposit_offline", "线下充值", DirectionEnum.in),

    withdraw("withdraw", "出金", DirectionEnum.out),

    withdraw_offline("withdraw_offline", "线下出金", DirectionEnum.out),

    transfer_out("transfer_out", "转出", DirectionEnum.out),

    transfer_in("transfer_in", "转入", DirectionEnum.in),

    freeze("freeze", "冻结", DirectionEnum.keep),

    unfreeze("unfreeze", "解冻", DirectionEnum.keep),;

    private final String code;
    private final String message;
    private DirectionEnum direction;


    private CommonTradeCodeEnum(String code, String message, DirectionEnum direction) {
        this.code = code;
        this.message = message;
        this.direction = direction;
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

    @Override
    public DirectionEnum direction() {
        return direction;
    }


    public static Map<String, String> mapping() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (CommonTradeCodeEnum type : values()) {
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
    public static CommonTradeCodeEnum find(String code) {
        for (CommonTradeCodeEnum status : values()) {
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
    public static List<CommonTradeCodeEnum> getAll() {
        List<CommonTradeCodeEnum> list = new ArrayList<CommonTradeCodeEnum>();
        for (CommonTradeCodeEnum status : values()) {
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
        for (CommonTradeCodeEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }

}
