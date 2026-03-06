package com.hms.roomreservationservice.service;

import com.hms.roomreservationservice.dto.RoomRequest;
import com.hms.roomreservationservice.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    RoomResponse create(RoomRequest request);

    RoomResponse update(Long id, RoomRequest request);

    List<RoomResponse> getAll();

    RoomResponse getById(Long id);

    void delete(Long id);
}
