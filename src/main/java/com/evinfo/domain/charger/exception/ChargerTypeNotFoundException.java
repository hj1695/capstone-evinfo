package com.evinfo.domain.charger.exception;

import com.evinfo.global.error.exception.EntityNotFoundException;

public class ChargerTypeNotFoundException extends EntityNotFoundException {
    public ChargerTypeNotFoundException(final Long key) {
        super(key + "에 해당하는 충전기 타입을 찾을 수 없습니다.");
    }
}
