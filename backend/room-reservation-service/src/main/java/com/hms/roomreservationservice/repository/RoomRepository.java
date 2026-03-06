package com.hms.roomreservationservice.repository;

import com.hms.roomreservationservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    
    boolean existsByRoomNumber(String roomNumber);
    
    @Query("SELECT r FROM Room r ORDER BY r.id DESC LIMIT 1")
    Optional<Room> findLastRoom();
}
