package com.hms.roomreservationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomFacilityResponse {

    private Long id;
    private String name;
}