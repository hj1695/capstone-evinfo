package com.evinfo.global.error.exception;

import com.evinfo.global.error.ErrorCode;

public abstract class EntityNotFoundException extends BusinessException {

    protected EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
