package com.evinfo.batch.charger.dto;

import com.evinfo.domain.charger.Charger;
import lombok.Getter;

@Getter
public class ChargerCreateRequestDto {
    private final String stationId;
    private final String chargerId;
    private final String chargerStat;
    private final String chargerType;

    public ChargerCreateRequestDto(Charger charger) {
        this.stationId = charger.getStation().getStationId();
        this.chargerId = charger.getChargerId();
        this.chargerStat = charger.getChargerStat().name();
        this.chargerType = charger.getChargerType().name();
    }
}
