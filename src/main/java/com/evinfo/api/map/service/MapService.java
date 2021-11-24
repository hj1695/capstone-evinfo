package com.evinfo.api.map.service;

import com.evinfo.api.map.dto.LocationCategoryResponseDto;
import com.evinfo.domain.map.LocationCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MapService {
    public List<LocationCategoryResponseDto> getLocationCategories() {
        return Arrays.stream(LocationCategory.values())
                .map(LocationCategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}