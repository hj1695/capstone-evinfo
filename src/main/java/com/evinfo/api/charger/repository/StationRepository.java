package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, String> {
    @Query("select a from Station a join fetch a.chargers")
    List<Station> findAllJoinFetch();

    @Query(value = "SELECT _station.*,\n" +
            "       (6371 * acos(\n" +
            "                       cos(radians(_station.latitude))\n" +
            "                       * cos(radians(:userLatitude))\n" +
            "                       * cos(radians(_station.longitude) - radians(:userLongitude))\n" +
            "                   + sin(radians(:userLatitude))\n" +
            "                           * sin(radians(_station.latitude))\n" +
            "           ))\n" +
            "           AS DISTANCE\n" +
            "FROM station _station\n" +
            "ORDER BY DISTANCE\n" +
            "LIMIT 0, :requestSize", nativeQuery = true)
    List<Station> findAllByPosition(Double userLatitude, Double userLongitude, Long requestSize);
}
