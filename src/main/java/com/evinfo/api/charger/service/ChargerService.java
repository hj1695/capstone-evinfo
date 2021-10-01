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

        return responses.subList(0, request.getSize().intValue());
    }

    private Double calculateDistance(Charger charger, ChargerRequestDto request) {
        return Math.sqrt(Math.pow(charger.getLongitude() - request.getLongitude(), 2.0)
                + Math.pow(charger.getLatitude() - request.getLatitude(), 2.0));
    }
}
