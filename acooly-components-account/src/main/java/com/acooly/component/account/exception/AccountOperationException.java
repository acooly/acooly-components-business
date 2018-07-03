package com.acooly.component.account.exception;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 16:19
 */
public class AccountOperationException extends BusinessException {

    public AccountOperationException() {
        super();
    }

    public AccountOperationException(String message) {
        super(message);
    }

    public AccountOperationException(String message, boolean writableStackTrace) {
        super(message, writableStackTrace);
    }

    public AccountOperationException(String message, String code, boolean writableStackTrace) {
        super(message, code, writableStackTrace);
    }

    public AccountOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountOperationException(Throwable cause) {
        super(cause);
    }

    public AccountOperationException(String message, String code) {
        super(message, code);
    }

    public AccountOperationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public AccountOperationException(Throwable cause, String code) {
        super(cause, code);
    }

    public AccountOperationException(Messageable messageable) {
        super(messageable);
    }

    public AccountOperationException(Messageable messageable, String msg) {
        super(messageable, msg);
    }

    public AccountOperationException(Messageable messageable, Throwable cause) {
        super(messageable, cause);
    }
}
