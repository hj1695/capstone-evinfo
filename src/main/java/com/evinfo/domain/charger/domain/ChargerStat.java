package com.evinfo.domain.charger.domain;

import com.evinfo.domain.charger.exception.ChargerStatNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ChargerStat {
    CONNECTION_ERROR(1L, "통신 이상"),
    WAITING(2L, "충전 대기"),
    CHARGING(3L, "충전중"),
    STOPPED(4L, "운영 중지"),
    CHECKING(5L, "점검중"),
    UNKNOWN(9L, "상태 미확인");

    private final Long key;
    private final String name;

    public static ChargerStat valueOf(final Long key) {
        return Arrays.stream(ChargerStat.values())
                .filter(chargerStat -> chargerStat.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new ChargerStatNotFoundException(key));
    }
}
