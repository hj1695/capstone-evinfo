package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import com.evinfo.domain.charger.ChargerType;
import com.evinfo.domain.charger.Station;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StationResponseDto {
    private final String stationName;
    private final String stationId;
    private final String address;
    private final String location;
    private final String useTime;
    private final Double latitude;
    private final Double longitude;
    private final String callNumber;
    private final String businessName;
    private final Double distance;
    private final Integer enableChargers;
    private final Boolean isLimit;
    private final Boolean isParkingFree;
    private final List<Long> chargerTypes;
    private final List<ChargerResponseDto> chargers;

    public StationResponseDto(final Station station, final Double distance) {
        this.stationName = station.getStationName();
        this.stationId = station.getStationId();
        this.address = station.getAddress();
        this.location = station.getLocation();
        this.useTime = station.getUseTime();
        this.latitude = station.getLatitude();
        this.longitude = station.getLongitude();
        this.callNumber = station.getCallNumber();
        this.businessName = station.getBusinessName();
        this.isLimit = station.getIsLimit();
        this.isParkingFree = station.getIsParkingFree();
        this.distance = distance;
        this.chargers = station.getChargers()
                .stream()
                .map(ChargerResponseDto::new)
                .collect(Collectors.toList());
        this.chargerTypes = station.getChargers()
                .stream()
                .map(Charger::getChargerType)
                .map(ChargerType::getKey)
                .distinct()
                .collect(Collectors.toList());
        this.enableChargers = (int) station.getChargers()
                .stream()
                .filter(charger -> charger.getChargerStat().equals(ChargerStat.WAITING))
                .count();
    }
}
