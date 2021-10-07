package com.evinfo.batch.charger.dto;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.ChargerStat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ChargerUpdateClientRequestDto {
    private final String stationId;
    private final String chargerId;
    private final ChargerStat chargerStat;

    public static ChargerUpdateClientRequestDto valueOf(final ChargerClientResponseDto clientResponse) {
        return ChargerUpdateClientRequestDto.builder()
                .stationId(clientResponse.getStatId())
                .chargerId(clientResponse.getChgerId())
                .chargerStat(ChargerStat.valueOf(clientResponse.getStat()))
                .build();
    }
}
