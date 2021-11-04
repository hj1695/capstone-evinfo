package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.ChargerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotNull
    Boolean isDCCombo;
    @NotNull
    Boolean isDCDemo;
    @NotNull
    Boolean isAC3;
    @NotNull
    Boolean isACSlow;

    public List<Long> getChargerTypeIds() {
        return ChargerType.getChargerTypesByBoolean(this.isDCCombo, this.isDCDemo, this.isAC3, this.isACSlow)
                .stream()
                .map(ChargerType::getKey)
                .collect(Collectors.toList());
    }
}
