package com.evinfo.api.map.dto;

import com.evinfo.domain.map.LocationCategory;
import lombok.Getter;

@Getter
public class LocationCategoryResponseDto {
    private final String key;
    private final String name;

    public LocationCategoryResponseDto(LocationCategory locationCategory) {
        this.key = locationCategory.getKey();
        this.name = locationCategory.getName();
    }
}
