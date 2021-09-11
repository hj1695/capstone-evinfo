package com.evinfo.domain.charger.dto;

import com.evinfo.domain.charger.domain.Charger;
import com.evinfo.domain.charger.domain.ChargerStat;
import com.evinfo.domain.charger.domain.ChargerType;
import lombok.Getter;

@Getter
public class ChargerResponseDto {
    private Long id;
    private String stationName;
    private String stationId;
    private ChargerType chargerType;
    private String address;
    private String location;
    private String useTime;
    private Double lat;
    private Double lng;
    private String callNumber;
    private ChargerStat chargerStat;

    public ChargerResponseDto(final Charger charger) {
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
    }
}
