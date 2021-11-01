package com.evinfo.api.charger.service;

import com.evinfo.api.charger.dto.ChargerTypeResponseDto;
import com.evinfo.domain.charger.ChargerType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChargerService {
    public List<ChargerTypeResponseDto> getChargerTypes() {
        return Arrays.stream(ChargerType.values())
                .map(ChargerTypeResponseDto::new)
                .collect(Collectors.toList());
    }
}
