package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.ChargerRequestDto;
import com.evinfo.api.charger.dto.ChargerResponseDto;
import com.evinfo.api.charger.service.ChargerClient;
import com.evinfo.api.charger.service.ChargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChargerController {
    private final ChargerClient chargerClient;
    private final ChargerService chargerService;

    @GetMapping("/chargers")
    public ResponseEntity<List<ChargerResponseDto>> getChargers(@Valid ChargerRequestDto request) {
        final List<ChargerResponseDto> responses = chargerService.getChargers(request);

        return ResponseEntity.ok()
                .body(responses);
    }
}
