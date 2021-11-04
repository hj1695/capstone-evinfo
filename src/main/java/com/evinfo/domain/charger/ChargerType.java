package com.evinfo.domain.charger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ChargerType {
    TYPE01(1L, "DC차 데모"),
    TYPE02(2L, "AC 완속"),
    TYPE03(3L, "DC차 데모 + AC3상"),
    TYPE04(4L, "DC 콤보"),
    TYPE05(5L, "DC차 데모 + DC 콤보"),
    TYPE06(6L, "DC차 데모 + AC3상 + DC 콤보"),
    TYPE07(7L, "AC3상"),
    UNKNOWN(9L, "알 수 없음");

    private final Long key;
    private final String name;

    public static ChargerType valueOf(final Long key) {
        return Arrays.stream(ChargerType.values())
                .filter(chargerType -> chargerType.getKey().equals(key))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public Boolean isDCCombo() {
        return this.key == 4L ||
                this.key == 5L ||
                this.key == 6L;
    }

    public Boolean isDCDemo() {
        return this.key == 1L ||
                this.key == 3L ||
                this.key == 5L ||
                this.key == 6L;
    }

    public Boolean isAC3() {
        return this.key == 3L ||
                this.key == 6L ||
                this.key == 7L;
    }

    public Boolean isACSlow() {
        return this.key == 2L;
    }

    public static List<ChargerType> getChargerTypesByBoolean(Boolean isDCCombo, Boolean isDCDemo, Boolean isAC3, Boolean isACSlow) {
        return Arrays.stream(ChargerType.values())
                .filter(chargerType -> {
                    if(isDCCombo && chargerType.isDCCombo()) {
                        return true;
                    }
                    if(isDCDemo && chargerType.isDCDemo()) {
                        return true;
                    }
                    if(isAC3 && chargerType.isAC3()) {
                        return true;
                    }
                    if(isACSlow && chargerType.isACSlow()) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
