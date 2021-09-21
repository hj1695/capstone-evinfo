package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;
import lombok.Getter;

@Getter
public class ChargerResponseDto {
    private final Long id;
    private final String stationName;
    private final String stationId;
    private final ChargerType chargerType;
    private final String address;
    private final String location;
    private final String useTime;
    private final Double lat;
    private final Double lng;
    private final String callNumber;
    private final ChargerStat chargerStat;
    private final Double distance;

    public ChargerResponseDto(final Charger charger, final Double distance) {
        this.id = charger.getId();
        this.stationName = charger.getStationName();
        this.stationId = charger.getStationId();
        this.chargerType = charger.getChargerType();
        this.address = charger.getAddress();
        this.location = charger.getLocation();
        this.useTime = charger.getUseTime();
        this.lat = charger.getLat();
        this.lng = charger.getLng();
        this.callNumber = charger.getCallNumber();
        this.chargerStat = charger.getChargerStat();
        this.distance = distance;
    }
}
