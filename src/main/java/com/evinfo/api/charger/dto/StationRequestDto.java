package com.evinfo.api.charger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationRequestDto {
    @NotNull
    Double latitude;
    @NotNull
    Double longitude;
    @NotNull
    @Max(1000)
    Long size;
    @NotEmpty
    List<Long> chargerTypes;
}
