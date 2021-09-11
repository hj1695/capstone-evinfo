package com.evinfo.domain.charger.exception;

public class ChargerTypeNotFoundException extends RuntimeException {
    public ChargerTypeNotFoundException(final Long key) {
        super(key + "에 해당하는 충전기 타입을 찾을 수 없습니다.");
    }
}
