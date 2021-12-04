package com.evinfo.api.charger.service;


import com.evinfo.api.charger.dto.StationBusinessResponseDto;
import com.evinfo.api.charger.dto.StationFilterRequestDto;
import com.evinfo.api.charger.dto.StationRequestDto;
import com.evinfo.api.charger.dto.StationResponseDto;
import com.evinfo.api.charger.repository.StationRepository;
import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerType;
import com.evinfo.domain.charger.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StationService {
    private final StationRepository stationRepository;
    private static final Double RADIUS = 6371.0;
    private static final Double KILOMETER_PER_METER = 1000.0;

    @Transactional(readOnly = true)
    public List<StationBusinessResponseDto> getStationBusinesses() {
        return stationRepository.findBusinessNameWithCount()
                .stream()
                .filter(b -> b.getCount() > 100)
                .map(b -> new StationBusinessResponseDto(b.getBusinessName(), b.getCount()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StationResponseDto> getStations(StationRequestDto request) {
        var chargerTypes = request.getChargerTypeIds();
        List<StationResponseDto> responses = stationRepository.findAllJoinFetch()
                .stream()
                .filter(station -> {
                    List<Long> types = station.getChargers()
                            .stream()
                            .map(Charger::getChargerType)
                            .map(ChargerType::getKey)
                            .collect(Collectors.toList());
                    return chargerTypes.containsAll(types);
                })
                .map(station -> new StationResponseDto(station, calculateDistance(station, request)))
                .sorted(Comparator.comparing(StationResponseDto::getDistance))
                .collect(Collectors.toList());

        if (request.getSize() > responses.size())
            return responses;
        return responses.subList(0, request.getSize().intValue());
    }

    @Transactional(readOnly = true)
    public List<StationResponseDto> getStationsWithBusiness(StationFilterRequestDto request) {
        var business = request.getBusinesses();
        List<StationResponseDto> responses = stationRepository.findAllJoinFetch()
                .stream()
                .parallel()
                .filter(station -> business.contains(station.getBusinessName()))
                .map(station -> new StationResponseDto(station, calculateDistance(station, request)))
                .sorted(Comparator.comparing(StationResponseDto::getDistance))
                .collect(Collectors.toList());

        if (request.getSize() > responses.size())
            return responses;
        return responses.subList(0, request.getSize().intValue());
    }

    private Double calculateDistance(Station station, StationRequestDto request) {
        double toRadian = Math.PI / 180.0;

        double deltaLatitude = Math.abs(station.getLatitude() - request.getLatitude()) * toRadian;
        double deltaLongitude = Math.abs(station.getLongitude() - request.getLongitude()) * toRadian;

        double sinDeltaLatitude = Math.sin(deltaLatitude / 2.0);
        double sinDeltaLongitude = Math.sin(deltaLongitude / 2.0);
        double squareRoot = Math.sqrt(
                sinDeltaLatitude * sinDeltaLatitude +
                        Math.cos(station.getLatitude() * toRadian) * Math.cos(station.getLatitude() * toRadian) * sinDeltaLongitude * sinDeltaLongitude);
        double distance = 2 * RADIUS * Math.asin(squareRoot);

        return Math.round(distance * KILOMETER_PER_METER) / KILOMETER_PER_METER;
    }

    private Double calculateDistance(Station station, StationFilterRequestDto request) {
        double toRadian = Math.PI / 180.0;

        double deltaLatitude = Math.abs(station.getLatitude() - request.getLatitude()) * toRadian;
        double deltaLongitude = Math.abs(station.getLongitude() - request.getLongitude()) * toRadian;

        double sinDeltaLatitude = Math.sin(deltaLatitude / 2.0);
        double sinDeltaLongitude = Math.sin(deltaLongitude / 2.0);
        double squareRoot = Math.sqrt(
                sinDeltaLatitude * sinDeltaLatitude +
                        Math.cos(station.getLatitude() * toRadian) * Math.cos(station.getLatitude() * toRadian) * sinDeltaLongitude * sinDeltaLongitude);
        double distance = 2 * RADIUS * Math.asin(squareRoot);

        return Math.round(distance * KILOMETER_PER_METER) / KILOMETER_PER_METER;
    }
}
