package com.hms.roomreservationservice.repository;

import com.hms.roomreservationservice.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<RoomReservation, Long> {

    Optional<RoomReservation> findByReservationNumber(String reservationNumber);

    @Query("SELECT r FROM RoomReservation r ORDER BY r.id DESC LIMIT 1")
    Optional<RoomReservation> findLastReservation();

    @Query("SELECT r FROM RoomReservation r WHERE r.room.id = :roomId " +
           "AND r.status NOT IN ('CANCELLED', 'CHECKED_OUT') " +
           "AND ((r.checkInDate <= :checkOut AND r.checkOutDate >= :checkIn))")
    List<RoomReservation> findConflictingReservations(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    List<RoomReservation> findByCustomerEmail(String customerEmail);
}
