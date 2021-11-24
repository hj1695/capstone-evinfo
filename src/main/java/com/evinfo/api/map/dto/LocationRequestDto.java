package com.evinfo.api.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotBlank
    private String category;
}
