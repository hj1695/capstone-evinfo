package com.evinfo.domain.charger.service;

import com.evinfo.domain.charger.dto.ChargerResponseDto;
import com.evinfo.domain.charger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChargerService {
    private final ChargerRepository chargerRepository;

    @Transactional(readOnly = true)
    public List<ChargerResponseDto> getChargers() {
        return chargerRepository.findAll()
                .stream()
                .map(ChargerResponseDto::new)
                .collect(Collectors.toList());
    }
}
