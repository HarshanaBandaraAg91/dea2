package com.hms.roomreservationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomFeatureResponse {

    private Long id;
    private String name;
}