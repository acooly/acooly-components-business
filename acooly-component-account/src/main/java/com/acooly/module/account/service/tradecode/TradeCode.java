package com.acooly.module.account.service.tradecode;

import com.acooly.module.account.enums.DirectionEnum;

/**
 * 交易码接口
 *
 * @author zhangpu
 * @date 2018-6-9
 */
public interface TradeCode {

    /**
     * 交易码
     *
     * @return
     */
    String code();

    /**
     * 交易名称
     *
     * @return
     */
    String message();


    DirectionEnum direction();

    /**
     * 交易说明
     *
     * @return
     */
    default String comments() {
        return null;
    }

    /**
     * 显示专用标签
     *
     * @return
     */
    default String lable() {
        return code() + "/" + message() + "/" + direction();
    }

    /**
     * 运算发号
     *
     * @return
     */
    default String symbol() {
        if (direction() == DirectionEnum.keep) {
            return "";
        }
        return direction() == DirectionEnum.in ? "+" : "-";
    }

}
