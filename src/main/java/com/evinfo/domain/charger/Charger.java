package com.evinfo.domain.charger;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

// TODO: 2021/09/09 추후 시간나면 validation + enum exception에 대한 도메인 테스트 구현
@Entity
@Getter
@Builder
@AllArgsConstructor
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

    @NotNull
    @Min(value = 0)
    private Long output;

    @NotNull
    @Min(value = 0)
    private Double price;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @PastOrPresent
    private LocalDateTime lastChargeDateTime;

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    @PastOrPresent
    private LocalDateTime startChargeDateTime;

    public void updateChargerStat(final ChargerStat chargerStat) {
        this.chargerStat = chargerStat;
    }

    public void updateLastChargeDateTime(LocalDateTime lastChargeDateTime) {
        this.lastChargeDateTime = lastChargeDateTime;
    }

    public void updateStartChargeDateTime(LocalDateTime startChargeDateTime) {
        this.startChargeDateTime = startChargeDateTime;
    }

    public void updateStation(final Station station) {
        this.station = station;
    }
}
