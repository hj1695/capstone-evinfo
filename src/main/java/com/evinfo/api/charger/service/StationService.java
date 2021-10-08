package com.evinfo.api.charger.service;


import com.evinfo.api.charger.dto.StationRequestDto;
import com.evinfo.api.charger.dto.StationResponseDto;
import com.evinfo.api.charger.repository.StationRepository;
import com.evinfo.domain.charger.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StationService {
    private final StationRepository stationRepository;
    private static final Double RADIUS = 6371.0;
    private static final Double KILOMETER_PER_METER = 1000.0;

    @Transactional(readOnly = true)
    public List<StationResponseDto> getStations(StationRequestDto request) {
        List<StationResponseDto> responses = stationRepository.findAllByPosition(
                request.getLatitude(),
                request.getLongitude(),
                request.getSize())
                .stream()
                .map(station -> {
                    Double distance = calculateDistance(station, request);

                    return new StationResponseDto(station, distance);
                })
                //.sorted(Comparator.comparing(StationResponseDto::getDistance))
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
}
