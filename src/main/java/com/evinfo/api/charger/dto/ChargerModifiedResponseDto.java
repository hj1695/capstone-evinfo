package com.evinfo.api.charger.dto;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.ChargerStat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ChargerModifiedResponseDto {
    private final String stationId;
    private final String chargerId;
    private final ChargerStat chargerStat;

    public static ChargerModifiedResponseDto valueOf(final ChargerClientResponseDto clientResponse) {
        return ChargerModifiedResponseDto.builder()
                .stationId(clientResponse.getStatId())
                .chargerId(clientResponse.getChgerId())
                .chargerStat(ChargerStat.valueOf(clientResponse.getStat()))
                .build();
    }
}
