package com.hms.roomreservationservice.repository;

import com.hms.roomreservationservice.model.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomFeatureRepository extends JpaRepository<RoomFeature, Long> {

    boolean existsByName(String name);
}