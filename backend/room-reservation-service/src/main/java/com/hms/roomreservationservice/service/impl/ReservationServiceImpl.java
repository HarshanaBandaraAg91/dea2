package com.hms.roomreservationservice.service.impl;

import com.hms.roomreservationservice.dto.*;
import com.hms.roomreservationservice.exception.ReservationNotFoundException;
import com.hms.roomreservationservice.model.Room;
import com.hms.roomreservationservice.model.RoomFacility;
import com.hms.roomreservationservice.model.RoomReservation;
import com.hms.roomreservationservice.repository.ReservationRepository;
import com.hms.roomreservationservice.repository.RoomFacilityRepository;
import com.hms.roomreservationservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements com.hms.roomreservationservice.service.ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final RoomFacilityRepository roomFacilityRepository;

    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        // Validate dates
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new RuntimeException("Check-out date must be after check-in date!");
        }

        // Find room
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));

        // Check if room is available
        List<RoomReservation> conflicts = reservationRepository.findConflictingReservations(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates!");
        }

        // Resolve additional facilities (not already on room)
        Set<RoomFacility> additionalFacilities = resolveAdditionalFacilities(room, request.getFacilityIds());

        // Calculate total price
        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        double totalPrice = numberOfNights * room.getPricePerNight();

        // Generate reservation number
        String reservationNumber = generateReservationNumber();

        // Create reservation
        RoomReservation reservation = RoomReservation.builder()
                .reservationNumber(reservationNumber)
                .room(room)
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerPhone())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(request.getNumberOfGuests())
                .roomCostPerNight(room.getPricePerNight())
                .additionalFacilities(additionalFacilities)
                .totalPrice(totalPrice)
                .status("CONFIRMED")
                .build();

        RoomReservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        RoomReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus().equals("CHECKED_IN") || reservation.getStatus().equals("CHECKED_OUT")) {
            throw new RuntimeException("Cannot update reservation with status: " + reservation.getStatus());
        }

        // Validate dates
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new RuntimeException("Check-out date must be after check-in date!");
        }

        // Find room
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));

        // Check availability (excluding current reservation)
        List<RoomReservation> conflicts = reservationRepository.findConflictingReservations(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        ).stream()
                .filter(r -> !r.getId().equals(id))
                .collect(Collectors.toList());

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Room is not available for the selected dates!");
        }

        // Resolve additional facilities (not already on room)
        Set<RoomFacility> additionalFacilities = resolveAdditionalFacilities(room, request.getFacilityIds());

        // Calculate total price
        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        double totalPrice = numberOfNights * room.getPricePerNight();

        // Update reservation
        reservation.setRoom(room);
        reservation.setCustomerName(request.getCustomerName());
        reservation.setCustomerEmail(request.getCustomerEmail());
        reservation.setCustomerPhone(request.getCustomerPhone());
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setNumberOfGuests(request.getNumberOfGuests());
        reservation.setRoomCostPerNight(room.getPricePerNight());
        reservation.setAdditionalFacilities(additionalFacilities);
        reservation.setTotalPrice(totalPrice);

        RoomReservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    @Override
    public ReservationResponse getById(Long id) {
        RoomReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));
        return mapToResponse(reservation);
    }

    @Override
    public ReservationResponse getByReservationNumber(String reservationNumber) {
        RoomReservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with number: " + reservationNumber));
        return mapToResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getByCustomerEmail(String email) {
        return reservationRepository.findByCustomerEmail(email)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        RoomReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus().equals("CHECKED_IN") || reservation.getStatus().equals("CHECKED_OUT")) {
            throw new RuntimeException("Cannot cancel reservation with status: " + reservation.getStatus());
        }

        reservation.setStatus("CANCELLED");
        RoomReservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public ReservationResponse checkIn(Long id) {
        RoomReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        if (!reservation.getStatus().equals("CONFIRMED")) {
            throw new RuntimeException("Only CONFIRMED reservations can be checked in!");
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(reservation.getCheckInDate())) {
            throw new RuntimeException("Check-in date has not arrived yet!");
        }

        reservation.setActualCheckInDate(today);
        reservation.setStatus("CHECKED_IN");
        RoomReservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public ReservationResponse checkOut(Long id) {
        RoomReservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with id: " + id));

        if (!reservation.getStatus().equals("CHECKED_IN")) {
            throw new RuntimeException("Only CHECKED_IN reservations can be checked out!");
        }

        LocalDate today = LocalDate.now();
        reservation.setActualCheckOutDate(today);

        // Recalculate total cost based on actual dates
        LocalDate actualCheckIn = reservation.getActualCheckInDate();
        long actualNights = ChronoUnit.DAYS.between(actualCheckIn, today);
        if (actualNights < 1) {
            actualNights = 1; // Minimum 1 night charge
        }
        double finalTotalPrice = actualNights * reservation.getRoomCostPerNight();
        reservation.setTotalPrice(finalTotalPrice);

        reservation.setStatus("CHECKED_OUT");
        RoomReservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    private ReservationResponse mapToResponse(RoomReservation reservation) {
        Room room = reservation.getRoom();
        
        RoomCategoryResponse categoryResponse = RoomCategoryResponse.builder()
                .id(room.getCategory().getId())
                .name(room.getCategory().getName())
                .build();

        Set<RoomFacilityResponse> facilityResponses = room.getFacilities().stream()
                .map(facility -> RoomFacilityResponse.builder()
                        .id(facility.getId())
                        .name(facility.getName())
                        .build())
                .collect(Collectors.toSet());

        RoomResponse roomResponse = RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .category(categoryResponse)
                .facilities(facilityResponses)
                .build();

        Set<RoomFacilityResponse> additionalFacilityResponses = reservation.getAdditionalFacilities().stream()
                .map(facility -> RoomFacilityResponse.builder()
                        .id(facility.getId())
                        .name(facility.getName())
                        .build())
                .collect(Collectors.toSet());

        return ReservationResponse.builder()
                .id(reservation.getId())
                .reservationNumber(reservation.getReservationNumber())
                .room(roomResponse)
                .customerName(reservation.getCustomerName())
                .customerEmail(reservation.getCustomerEmail())
                .customerPhone(reservation.getCustomerPhone())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .actualCheckInDate(reservation.getActualCheckInDate())
                .actualCheckOutDate(reservation.getActualCheckOutDate())
                .numberOfGuests(reservation.getNumberOfGuests())
                .additionalFacilities(additionalFacilityResponses)
                .roomCostPerNight(reservation.getRoomCostPerNight())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }

    private Set<RoomFacility> resolveAdditionalFacilities(Room room, Set<Long> facilityIds) {
        if (facilityIds == null || facilityIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<RoomFacility> facilities = new HashSet<>(roomFacilityRepository.findAllById(facilityIds));
        if (facilities.size() != facilityIds.size()) {
            throw new RuntimeException("One or more facilities not found!");
        }

        Set<Long> roomFacilityIds = room.getFacilities().stream()
                .map(RoomFacility::getId)
                .collect(Collectors.toSet());

        if (facilities.stream().anyMatch(f -> roomFacilityIds.contains(f.getId()))) {
            throw new RuntimeException("Selected facilities already included in the room. Remove duplicates.");
        }

        Set<String> categoryFeatureNames = room.getCategory().getFeatures().stream()
                .map(f -> f.getName().toLowerCase())
                .collect(Collectors.toSet());

        if (facilities.stream().anyMatch(f -> categoryFeatureNames.contains(f.getName().toLowerCase()))) {
            throw new RuntimeException("Selected facilities already included in the room category.");
        }

        return facilities;
    }

    private String generateReservationNumber() {
        Optional<RoomReservation> lastReservation = reservationRepository.findLastReservation();
        if (lastReservation.isPresent()) {
            String lastNumber = lastReservation.get().getReservationNumber();
            int nextNumber = Integer.parseInt(lastNumber.substring(3)) + 1;
            return String.format("RES%05d", nextNumber);
        }
        return "RES00001";
    }
}

