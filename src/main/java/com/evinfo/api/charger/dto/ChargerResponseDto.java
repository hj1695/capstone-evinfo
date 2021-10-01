package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;
import lombok.Getter;

@Getter
public class ChargerResponseDto {
    private final String stationName;
    private final String stationId;
    private final String chargerId;
    private final ChargerType chargerType;
    private final String address;
    private final String location;
    private final String useTime;
    private final Double latitude;
    private final Double longitude;
    private final String callNumber;
    private final ChargerStat chargerStat;
    private final Double distance;

    public ChargerResponseDto(final Charger charger, final Double distance) {
        this.stationName = charger.getStationName();
        this.stationId = charger.getStationId();
        this.chargerId = charger.getChargerId();
        this.chargerType = charger.getChargerType();
        this.address = charger.getAddress();
        this.location = charger.getLocation();
        this.useTime = charger.getUseTime();
        this.latitude = charger.getLatitude();
        this.longitude = charger.getLongitude();
        this.callNumber = charger.getCallNumber();
        this.chargerStat = charger.getChargerStat();
        this.distance = distance;
    }
}
