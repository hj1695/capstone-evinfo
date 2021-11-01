package com.evinfo.api.charger.repository;

import com.evinfo.domain.charger.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, String> {
    @Query("select distinct a from Station a join fetch a.chargers")
    List<Station> findAllJoinFetch();
}
