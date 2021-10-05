package com.evinfo.domain.charger;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    @OneToMany(mappedBy = "station")
    private List<Charger> chargers;

    public void addCharger(final Charger charger) {
        this.chargers.add(charger);
        charger.updateStation(this);
    }
}
