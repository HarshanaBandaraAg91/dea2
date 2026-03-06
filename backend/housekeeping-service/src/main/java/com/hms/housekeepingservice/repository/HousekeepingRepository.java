package com.hms.housekeepingservice.repository;

import com.hms.housekeepingservice.entity.HousekeepingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HousekeepingRepository
        extends JpaRepository<HousekeepingTask, Long> {

    List<HousekeepingTask> findByRoomNumber(String roomNumber);
    List<HousekeepingTask> findByStatus(String status);
}
