package com.evinfo.domain.charger;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station {
    @Id
    @NotBlank
    private String stationId;

    @NotBlank
    private String stationName;

    @NotBlank
    private String address;

    @NotEmpty
    private String location;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotBlank
    private String callNumber;

    @NotNull
    private String useTime;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<Charger> chargers;

    @Builder
    public Station(String stationId, String stationName, String address, String location, Double latitude, Double longitude, String callNumber, String useTime) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.address = address;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.callNumber = callNumber;
        this.useTime = useTime;
        this.chargers = new ArrayList<>();
    }

    public void addCharger(final Charger charger) {
        this.chargers.add(charger);
        charger.updateStation(this);
    }
}
