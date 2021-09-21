package com.evinfo.api.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerRequestDto {
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    @Max(1000)
    Long size;
}
