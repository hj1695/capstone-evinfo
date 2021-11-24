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
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    @Max(1000)
    private Long size;
    @NotNull
    private Boolean isDCCombo;
    @NotNull
    private Boolean isDCDemo;
    @NotNull
    private Boolean isAC3;
    @NotNull
    private Boolean isACSlow;

    public List<Long> getChargerTypeIds() {
        return ChargerType.getChargerTypesByBoolean(this.isDCCombo, this.isDCDemo, this.isAC3, this.isACSlow)
                .stream()
                .map(ChargerType::getKey)
                .collect(Collectors.toList());
    }
}
