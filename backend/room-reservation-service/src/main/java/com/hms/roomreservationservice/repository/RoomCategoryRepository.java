package com.hms.roomreservationservice.repository;

import com.hms.roomreservationservice.model.RoomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Long> {

    boolean existsByName(String name);
}