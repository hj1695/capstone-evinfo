package com.evinfo.batch.charger.dto;

import com.evinfo.api.charger.dto.client.ChargerClientResponseDto;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.global.utils.DateTimeTranslator;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class ChargerUpdateRequestDto {
    private final String stationId;
    private final String chargerId;
    private final ChargerStat chargerStat;
    private final LocalDateTime lastChargeDateTime;
    private final LocalDateTime startChargeDateTime;

    public static ChargerUpdateRequestDto valueOf(final ChargerClientResponseDto clientResponse) {
        return ChargerUpdateRequestDto.builder()
                .stationId(clientResponse.getStatId())
                .chargerId(clientResponse.getChgerId())
                .chargerStat(ChargerStat.valueOf(clientResponse.getStat()))
                .lastChargeDateTime(DateTimeTranslator.exchangeLocalDateTime(clientResponse.getLastTedt()))
                .startChargeDateTime(DateTimeTranslator.exchangeLocalDateTime(clientResponse.getStatUpdDt()))
                .build();
    }
}
