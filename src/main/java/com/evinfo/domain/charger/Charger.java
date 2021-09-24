package com.evinfo.domain.charger;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO: 2021/09/09 추후 시간나면 validation + enum exception에 대한 도메인 테스트 구현
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ChargerCompositeId.class)
public class Charger {
    @NotBlank
    private String stationName;

    @Id
    @NotBlank
    private String stationId;

    @Id
    @NotBlank
    private String chargerId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChargerType chargerType;

    @NotBlank
    private String address;

    @NotNull
    private String location;

    @NotNull
    private String useTime;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotBlank
    private String callNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChargerStat chargerStat;
}
