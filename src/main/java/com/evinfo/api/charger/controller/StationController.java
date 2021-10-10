package com.evinfo.api.charger.controller;

import com.evinfo.api.charger.dto.StationRequestDto;
import com.evinfo.api.charger.dto.StationResponseDto;
import com.evinfo.api.charger.service.StationService;
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
public class StationController {
    private final StationService stationService;

    @GetMapping("/stations")
    public ResponseEntity<List<StationResponseDto>> getStations(@Valid StationRequestDto request) {
        final List<StationResponseDto> responses = stationService.getStations(request);

        return ResponseEntity.ok()
                .body(responses);
    }
}
