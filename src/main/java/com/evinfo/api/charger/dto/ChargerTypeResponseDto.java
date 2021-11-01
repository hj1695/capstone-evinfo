package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.ChargerType;
import lombok.Getter;

@Getter
public class ChargerTypeResponseDto {
    private final Long key;
    private final String name;

    public ChargerTypeResponseDto(ChargerType chargerType) {
        this.key = chargerType.getKey();
        this.name = chargerType.getName();
    }
}
