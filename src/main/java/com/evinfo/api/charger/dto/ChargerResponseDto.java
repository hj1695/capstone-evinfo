package com.evinfo.api.charger.dto;

import com.evinfo.domain.charger.Charger;
import com.evinfo.domain.charger.ChargerStat;
import lombok.Getter;

@Getter
public class ChargerResponseDto {
    private final String chargerId;
    private final Boolean isDCCombo;
    private final Boolean isDCDemo;
    private final Boolean isAC3;
    private final Boolean isACSlow;
    private final ChargerStat chargerStat;
    private final Long output;
    private final Double price;

    public ChargerResponseDto(final Charger charger) {
        this.chargerId = charger.getChargerId();
        this.chargerStat = charger.getChargerStat();
        this.isDCCombo = charger.getChargerType().isDCCombo();
        this.isDCDemo = charger.getChargerType().isDCDemo();
        this.isAC3 = charger.getChargerType().isAC3();
        this.isACSlow = charger.getChargerType().isACSlow();
        this.output = charger.getOutput();
        this.price = charger.getPrice();
    }
}
