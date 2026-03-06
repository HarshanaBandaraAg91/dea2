package com.hms.roomreservationservice.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

    private Long id;
    private String roomNumber;
    private Double pricePerNight;
    private String status;
    private RoomCategoryResponse category;
    private Set<RoomFacilityResponse> facilities;
}
