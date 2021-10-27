package com.evinfo.batch.charger.dto;

import com.evinfo.domain.charger.Charger;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChargerCreateRequestDto {
    private final String stationId;
    private final String chargerId;
    private final String chargerStat;
    private final String chargerType;
    private final Long output;
    private final Double price;
    private final LocalDateTime lastChargeDateTime;
    private final LocalDateTime startChargeDateTime;

    public ChargerCreateRequestDto(Charger charger) {
        this.stationId = charger.getStation().getStationId();
        this.chargerId = charger.getChargerId();
        this.chargerStat = charger.getChargerStat().name();
        this.chargerType = charger.getChargerType().name();
        this.output = charger.getOutput();
        this.price = charger.getPrice();
        this.lastChargeDateTime = charger.getLastChargeDateTime();
        this.startChargeDateTime = charger.getStartChargeDateTime();
    }
}
