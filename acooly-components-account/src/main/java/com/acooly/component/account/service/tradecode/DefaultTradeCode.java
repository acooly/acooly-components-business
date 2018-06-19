package com.acooly.component.account.service.tradecode;

import com.acooly.component.account.enums.DirectionEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-06-12 15:47
 */
@Getter
@Setter
public class DefaultTradeCode implements TradeCode {

    private String code;

    private String message;

    private DirectionEnum direction;

    public DefaultTradeCode() {
    }

    public DefaultTradeCode(String code, String message, DirectionEnum direction) {
        this.code = code;
        this.message = message;
        this.direction = direction;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public DirectionEnum direction() {
        return this.direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultTradeCode that = (DefaultTradeCode) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }
}
