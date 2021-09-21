package com.evinfo.domain.charger.service;

import com.evinfo.domain.charger.domain.Charger;
import com.evinfo.domain.charger.dto.ChargerRequestDto;
import com.evinfo.domain.charger.dto.ChargerResponseDto;
import com.evinfo.domain.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Transactional(readOnly = true)
    public List<ChargerResponseDto> getChargers(ChargerRequestDto request) {
        List<Charger> chargers = chargerRepository.findAll();
        Map<Double, Charger> distanceSortedMap = getDistanceSortedMap(request, chargers);
        List<ChargerResponseDto> responses = new ArrayList<>();

        for (var entry : distanceSortedMap.entrySet()) {
            responses.add(new ChargerResponseDto(entry.getValue()));
            if (responses.size() >= request.getSize()) break;
        }

        return responses;
    }

    private Map<Double, Charger> getDistanceSortedMap(ChargerRequestDto request, List<Charger> chargers) {
        Map<Double, Charger> distanceMap = new TreeMap<>();
        for (var charger : chargers) {
            Double distance = calculateDistance(charger, request);
            distanceMap.put(distance, charger);
        }
        return distanceMap;
    }

    private Double calculateDistance(Charger charger, ChargerRequestDto request) {
        return Math.sqrt(Math.pow(charger.getLng() - request.getLongitude(), 2.0)
                + Math.pow(charger.getLat() - request.getLatitude(), 2.0));
    }
}
