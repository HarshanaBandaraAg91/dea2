package com.hms.roomreservationservice.service;

import com.hms.roomreservationservice.dto.ReservationRequest;
import com.hms.roomreservationservice.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);

    ReservationResponse updateReservation(Long id, ReservationRequest request);

    ReservationResponse getById(Long id);

    ReservationResponse getByReservationNumber(String reservationNumber);

    List<ReservationResponse> getAll();

    List<ReservationResponse> getByCustomerEmail(String email);

    ReservationResponse cancelReservation(Long id);

    ReservationResponse checkIn(Long id);

    ReservationResponse checkOut(Long id);
}
