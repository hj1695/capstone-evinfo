package com.evinfo.domain.charger.exception;

public class ChargerStatNotFoundException extends RuntimeException {
    public ChargerStatNotFoundException(final Long key) {
        super(key + "에 해당하는 충전기 상태를 찾을 수 없습니다.");
    }
}
