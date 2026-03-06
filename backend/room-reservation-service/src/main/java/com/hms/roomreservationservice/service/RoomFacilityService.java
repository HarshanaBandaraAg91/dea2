package com.hms.roomreservationservice.service;

import com.hms.roomreservationservice.dto.RoomFacilityRequest;
import com.hms.roomreservationservice.dto.RoomFacilityResponse;

import java.util.List;

public interface RoomFacilityService {

    RoomFacilityResponse create(RoomFacilityRequest request);

    RoomFacilityResponse update(Long id, RoomFacilityRequest request);

    List<RoomFacilityResponse> getAll();

    RoomFacilityResponse getById(Long id);

    void delete(Long id);
}