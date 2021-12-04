package com.evinfo.domain.charger;

import com.evinfo.domain.review.Review;
import lombok.*;

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

    @NotBlank
    private String businessName;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private final List<Charger> chargers = new ArrayList<>();

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private final List<Review> reviews = new ArrayList<>();

    @NotNull
    private Boolean isLimit;
    @NotNull
    private Boolean isParkingFree;

    public void addCharger(final Charger charger) {
        this.chargers.add(charger);
        charger.updateStation(this);
    }

    public void addReview(final Review review) {
        this.reviews.add(review);
    }
}
