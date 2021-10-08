package com.evinfo.domain.charger;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChargerCompositeId implements Serializable {
    private String station;
    private String chargerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargerCompositeId chargerId1 = (ChargerCompositeId) o;
        return station.equals(chargerId1.station) && chargerId.equals(chargerId1.chargerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station, chargerId);
    }
}
