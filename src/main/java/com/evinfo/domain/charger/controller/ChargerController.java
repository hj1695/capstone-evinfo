package com.evinfo.domain.charger.controller;

import com.evinfo.domain.charger.dto.ChargerResponseDto;
import com.evinfo.domain.charger.service.ChargerClient;
import com.evinfo.domain.charger.service.ChargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChargerController {
    private final ChargerClient chargerClient;
    private final ChargerService chargerService;

    @GetMapping("/chargers")
    public ResponseEntity<List<ChargerResponseDto>> getChargers() {
        chargerClient.fetchChargers();
        final List<ChargerResponseDto> responses = chargerService.getChargers();

        return ResponseEntity.ok()
                .body(responses);
    }
}
