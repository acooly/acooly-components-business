package com.acooly.module.account.exception;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 16:19
 */
public class AccountVerifyException extends BusinessException {

    public AccountVerifyException() {
        super();
    }

    public AccountVerifyException(String message) {
        super(message);
    }

    public AccountVerifyException(String message, boolean writableStackTrace) {
        super(message, writableStackTrace);
    }

    public AccountVerifyException(String message, String code, boolean writableStackTrace) {
        super(message, code, writableStackTrace);
    }

    public AccountVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountVerifyException(Throwable cause) {
        super(cause);
    }

    public AccountVerifyException(String message, String code) {
        super(message, code);
    }

    public AccountVerifyException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public AccountVerifyException(Throwable cause, String code) {
        super(cause, code);
    }

    public AccountVerifyException(Messageable messageable) {
        super(messageable);
    }

    public AccountVerifyException(Messageable messageable, String msg) {
        super(messageable, msg);
    }

    public AccountVerifyException(Messageable messageable, Throwable cause) {
        super(messageable, cause);
    }
}
