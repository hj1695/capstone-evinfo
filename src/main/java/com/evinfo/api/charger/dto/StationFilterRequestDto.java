package com.evinfo.api.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationFilterRequestDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    @Max(1000)
    private Long size;
    @NotNull
    private List<String> businesses;
}
