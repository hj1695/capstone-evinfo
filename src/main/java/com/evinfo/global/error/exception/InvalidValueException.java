package com.evinfo.global.error.exception;

import com.evinfo.global.error.ErrorCode;

public abstract class InvalidValueException extends BusinessException {

    protected InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }

    protected InvalidValueException(String value, ErrorCode errorCode) {
        super(value, errorCode);
    }
}
