package com.evinfo.domain.charger.controller;

import com.evinfo.domain.charger.dto.ChargerResponseDto;
import com.evinfo.domain.charger.service.ChargerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChargerController {
    private final ChargerClient chargerClient;

    @GetMapping("/chargers")
    public ResponseEntity<List<ChargerResponseDto>> getChargers() {
        final List<ChargerResponseDto> responses = chargerClient.getChargers()
                .stream()
                .map(ChargerResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(responses);
    }
}
