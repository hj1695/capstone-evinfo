package com.evinfo.domain.charger.domain;

<<<<<<< HEAD
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import lombok.*;
>>>>>>> feat: Charger 클래스와 Response DTO 클래스 구현

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO: 2021/09/09 추후 시간나면 validation + enum exception에 대한 도메인 테스트 구현
@Entity
@Getter
<<<<<<< HEAD
=======
@Builder
@AllArgsConstructor
>>>>>>> feat: Charger 클래스와 Response DTO 클래스 구현
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

<<<<<<< HEAD
    @NotBlank
=======
>>>>>>> feat: Charger 클래스와 Response DTO 클래스 구현
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
