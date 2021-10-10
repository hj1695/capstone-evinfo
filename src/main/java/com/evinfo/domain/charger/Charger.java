package com.evinfo.domain.charger;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO: 2021/09/09 추후 시간나면 validation + enum exception에 대한 도메인 테스트 구현
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChargerCompositeId.class)
public class Charger {
    @Id
    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private Station station;

    @Id
    @NotBlank
    private String chargerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChargerType chargerType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChargerStat chargerStat;

    @Builder
    public Charger(Station station, String chargerId, ChargerType chargerType, ChargerStat chargerStat) {
        this.station = station;
        this.chargerId = chargerId;
        this.chargerType = chargerType;
        this.chargerStat = chargerStat;
    }

    public void updateChargerStat(final ChargerStat chargerStat) {
        this.chargerStat = chargerStat;
    }

    public void updateStation(final Station station) {
        this.station = station;
    }
}
