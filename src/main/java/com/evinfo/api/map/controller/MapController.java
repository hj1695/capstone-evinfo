package com.evinfo.api.map.controller;

import com.evinfo.api.map.dto.LocationCategoryResponseDto;
import com.evinfo.api.map.dto.LocationRequestDto;
import com.evinfo.api.map.dto.LocationResponseDto;
import com.evinfo.api.map.service.MapClient;
import com.evinfo.api.map.service.MapService;
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
public class MapController {
    private final MapClient mapClient;
    private final MapService mapService;

    @GetMapping("/locations")
    public ResponseEntity<List<LocationResponseDto>> getLocations(@Valid LocationRequestDto request) {
        final List<LocationResponseDto> responses = mapClient.getMapDocuments(request.getLongitude(), request.getLatitude(), request.getCategory());

        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/locations/categories")
    public ResponseEntity<List<LocationCategoryResponseDto>> getLocationCategories() {
        final List<LocationCategoryResponseDto> responses = mapService.getLocationCategories();

        return ResponseEntity.ok()
                .body(responses);
    }
}
