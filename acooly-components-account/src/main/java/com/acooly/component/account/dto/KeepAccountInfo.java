package com.acooly.component.account.dto;

import com.acooly.component.account.service.tradecode.TradeCode;
import com.acooly.core.utils.Money;
import lombok.Getter;
import lombok.Setter;


/**
 * 记账信息
 *
 * @author zhangpu@acooly.cn
 * @date 2018-06-19
 */
@Getter
@Setter
public class KeepAccountInfo {

    private String username;

    private TradeCode tradeCode;

    private Money amount;

    private String comments;

}
