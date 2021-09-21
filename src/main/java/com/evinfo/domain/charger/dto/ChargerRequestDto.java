package com.evinfo.domain.charger.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChargerRequestDto {
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    @Max(1000)
    Long size;
}
