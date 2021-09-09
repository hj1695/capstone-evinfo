package com.evinfo.domain.charger.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO: 2021/09/09 추후 시간나면 validation + enum exception에 대한 도메인 테스트 구현
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Charger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String statName;

    @NotBlank
    private String statId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChargerType chargerType;

    @NotBlank
    private String address;

    @NotBlank
    private String location;

    @NotBlank
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
