package com.acooly.component.account.service.tradecode;

import com.acooly.component.account.enums.DirectionEnum;

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

    ;



}
