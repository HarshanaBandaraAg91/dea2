package com.hms.roomreservationservice.service;

import com.hms.roomreservationservice.dto.RoomFeatureRequest;
import com.hms.roomreservationservice.dto.RoomFeatureResponse;

import java.util.List;

public interface RoomFeatureService {

    RoomFeatureResponse create(RoomFeatureRequest request);

    RoomFeatureResponse update(Long id, RoomFeatureRequest request);

    List<RoomFeatureResponse> getAll();

    RoomFeatureResponse getById(Long id);

    void delete(Long id);
}