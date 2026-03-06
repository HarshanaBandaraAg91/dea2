package com.hms.roomreservationservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private Long id;
    private String reservationNumber;
    private RoomResponse room;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate actualCheckInDate;
    private LocalDate actualCheckOutDate;
    private Integer numberOfGuests;
    private Set<RoomFacilityResponse> additionalFacilities;
    private Double roomCostPerNight;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
