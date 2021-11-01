package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.ChargerTypeResponseDto;
import com.evinfo.api.charger.service.ChargerService;
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
    private final ChargerService chargerService;

    @GetMapping("/chargers/types")
    public ResponseEntity<List<ChargerTypeResponseDto>> getChargerTypes() {
        final List<ChargerTypeResponseDto> responses = chargerService.getChargerTypes();

        return ResponseEntity.ok()
                .body(responses);
    }
}
