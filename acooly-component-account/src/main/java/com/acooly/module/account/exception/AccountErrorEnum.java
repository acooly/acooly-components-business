package com.acooly.module.account.exception;

import com.acooly.core.utils.enums.Messageable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 16:20
 */
public enum AccountErrorEnum implements Messageable {


    ACCOUNT_INTERNAL_ERROR("ACCOUNT_UNKNOWN_ERROR", "账务内部错误"),

    ACCOUNT_NOT_EXIST("ACCOUNT_NOT_EXIST", "账户不存在"),

    ACCOUNT_ALREADT_EXISTED("ACCOUNT_NOT_EXIST", "账户已存在"),

    ACCOUNT_STATUS_ERROR("ACCOUNT_STATUS_ERROR", "用户状态不正常"),

    ACCOUNT_STATUS_NOT_ALLOW_CHANGE("ACCOUNT_STATUS_NOT_ALLOW_CHANGE", "用户状态不允许修改"),

    ACCOUNT_INSUFFICIENT_BALANCE("ACCOUNT_INSUFFICIENT_BALANCE", "账户余额不足"),

    ACCOUNT_INSUFFICIENT_FREEZE("ACCOUNT_INSUFFICIENT_FREEZE", "账冻结总额不足"),

    ACCOUNT_BATCH_LIST_IS_EMPTY("ACCOUNT_BATCH_LIST_IS_EMPTY", "批量记账列表为空"),

    ACCOUNT_BATCH_AMOUNT_SUM_MISMATCH("ACCOUNT_BATCH_AMOUNT_SUM_MISMATCH", "批量记账的交易总额不匹配（绝对值不为零）"),

    ACCOUNT_BATCH_NOT_ALLOW_OVER_MAX("ACCOUNT_BATCH_NOT_ALLOW_OVER_MAX", "批量记账数量超过单批最大值"),

    ACCOUNT_BATCH_KEEP_DIFFERENT("ACCOUNT_BATCH_KEEP_DIFFERENT", "批量记账批次号不一致"),

    ACCOUNT_VERIFY_LATEST_BALANCE_FAIL("ACCOUNT_VERIFY_LATEST_BALANCE_FAIL", "最新交易流水交易后余额与账户余额不一致"),

    ACCOUNT_VERIFY_BILL_FAIL("ACCOUNT_VERIFY_BILL_FAIL", "账户流水验证未通过"),;


    private final String code;
    private final String message;

    private AccountErrorEnum(String code, String message) {
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
        for (AccountErrorEnum type : values()) {
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
    public static AccountErrorEnum find(String code) {
        for (AccountErrorEnum status : values()) {
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
    public static List<AccountErrorEnum> getAll() {
        List<AccountErrorEnum> list = new ArrayList<AccountErrorEnum>();
        for (AccountErrorEnum status : values()) {
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
        for (AccountErrorEnum status : values()) {
            list.add(status.code());
        }
        return list;
    }

}
