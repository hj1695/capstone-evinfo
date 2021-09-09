package com.evinfo.domain.charger.domain;

import com.evinfo.domain.charger.exception.ChargerTypeNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ChargerType {
    TYPE01(1L, "DC차 데모"),
    TYPE02(2L, "AC 완속"),
    TYPE03(3L, "DC차 데모 + AC3상"),
    TYPE04(4L, "DC 콤보"),
    TYPE05(5L, "DC차 데모 + DC 콤보"),
    TYPE06(6L, "DC차 데모 + AC3상 + DC 콤보"),
    TYPE07(7L, "AC3상");

    private final Long key;
    private final String name;

    public static ChargerType valueOf(final Long key) {
        return Arrays.stream(ChargerType.values())
                .filter(chargerType -> chargerType.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new ChargerTypeNotFoundException(key));
    }
}
