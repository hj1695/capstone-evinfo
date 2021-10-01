package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.ChargerRequestDto;
import com.evinfo.api.charger.dto.ChargerResponseDto;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.domain.charger.Charger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;
    private static final Double RADIUS = 6371.0;
    private static final Double KILOMETER_PER_METER = 1000.0;

    @Transactional(readOnly = true)
    public List<ChargerResponseDto> getChargers(ChargerRequestDto request) {
        List<ChargerResponseDto> responses = chargerRepository.findAll()
                .stream()
                .map(charger -> {
                    Double distance = calculateDistance(charger, request);
                    return new ChargerResponseDto(charger, distance);
                })
                .sorted(Comparator.comparing(ChargerResponseDto::getDistance))
                .collect(Collectors.toList());
        if (request.getSize() > responses.size())
            return responses;
        return responses.subList(0, request.getSize().intValue());
    }

    private Double calculateDistance(Charger charger, ChargerRequestDto request) {
        double toRadian = Math.PI / 180.0;

        double deltaLatitude = Math.abs(charger.getLatitude() - request.getLatitude()) * toRadian;
        double deltaLongitude = Math.abs(charger.getLongitude() - request.getLongitude()) * toRadian;

        double sinDeltaLatitude = Math.sin(deltaLatitude / 2.0);
        double sinDeltaLongitude = Math.sin(deltaLongitude / 2.0);
        double squareRoot = Math.sqrt(
                sinDeltaLatitude * sinDeltaLatitude +
                        Math.cos(charger.getLatitude() * toRadian) * Math.cos(charger.getLatitude() * toRadian) * sinDeltaLongitude * sinDeltaLongitude);
        double distance = 2 * RADIUS * Math.asin(squareRoot);

        return Math.round(distance * KILOMETER_PER_METER) / KILOMETER_PER_METER;
    }
}
