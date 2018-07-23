package com.acooly.module.member.exception;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.Messageable;

/**
 * @author zhangpu@acooly.cn
 * @date 2018-07-01 16:19
 */
public class MemberOperationException extends BusinessException {

    public MemberOperationException() {
        super();
    }

    public MemberOperationException(String message) {
        super(message);
    }

    public MemberOperationException(String message, boolean writableStackTrace) {
        super(message, writableStackTrace);
    }

    public MemberOperationException(String message, String code, boolean writableStackTrace) {
        super(message, code, writableStackTrace);
    }

    public MemberOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberOperationException(Throwable cause) {
        super(cause);
    }

    public MemberOperationException(String message, String code) {
        super(message, code);
    }

    public MemberOperationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public MemberOperationException(Throwable cause, String code) {
        super(cause, code);
    }

    public MemberOperationException(Messageable messageable) {
        super(messageable);
    }

    public MemberOperationException(Messageable messageable, String msg) {
        super(messageable, msg);
    }

    public MemberOperationException(Messageable messageable, Throwable cause) {
        super(messageable, cause);
    }
}
