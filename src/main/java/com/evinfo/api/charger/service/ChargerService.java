package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.ChargerRequestDto;
import com.evinfo.api.charger.dto.ChargerResponseDto;
import com.evinfo.api.charger.repository.ChargerRepository;
import com.evinfo.domain.charger.Charger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Transactional(readOnly = true)
    public List<ChargerResponseDto> getChargers(ChargerRequestDto request) {
        Map<Double, ChargerResponseDto> distanceSortedMap = chargerRepository.findAll()
                .stream()
                .map(charger -> {
                    Double distance = calculateDistance(charger, request);
                    return new ChargerResponseDto(charger, distance);
                })
                .collect(Collectors.toMap(ChargerResponseDto::getDistance, Function.identity(), (o1, o2) -> o1, TreeMap::new));
        List<ChargerResponseDto> responses = new ArrayList<>();

        for (var entry : distanceSortedMap.entrySet()) {
            responses.add(entry.getValue());
            if (responses.size() >= request.getSize()) break;
        }

        return responses;
    }

    private Double calculateDistance(Charger charger, ChargerRequestDto request) {
        return Math.sqrt(Math.pow(charger.getLng() - request.getLongitude(), 2.0)
                + Math.pow(charger.getLat() - request.getLatitude(), 2.0));
    }
}
