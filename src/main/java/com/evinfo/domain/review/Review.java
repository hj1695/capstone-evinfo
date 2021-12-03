package com.evinfo.domain.review;

import com.evinfo.domain.charger.Station;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @NotNull // TODO: 2021/12/03 Double은 Min/Max 체크 안됨. validation할 방법 찾기.
    private Double star;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private Station station;
}
