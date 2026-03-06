package com.hms.roomreservationservice.controller;

import com.hms.roomreservationservice.dto.ReservationRequest;
import com.hms.roomreservationservice.dto.ReservationResponse;
import com.hms.roomreservationservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-reservation/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return service.createReservation(request);
    }

    @PutMapping("/{id}")
    public ReservationResponse update(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        return service.updateReservation(id, request);
    }

    @GetMapping
    public List<ReservationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ReservationResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/number/{reservationNumber}")
    public ReservationResponse getByReservationNumber(@PathVariable String reservationNumber) {
        return service.getByReservationNumber(reservationNumber);
    }

    @GetMapping("/customer/{email}")
    public List<ReservationResponse> getByCustomerEmail(@PathVariable String email) {
        return service.getByCustomerEmail(email);
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable Long id) {
        return service.cancelReservation(id);
    }

    @PutMapping("/{id}/check-in")
    public ReservationResponse checkIn(@PathVariable Long id) {
        return service.checkIn(id);
    }

    @PutMapping("/{id}/check-out")
    public ReservationResponse checkOut(@PathVariable Long id) {
        return service.checkOut(id);
    }
}
