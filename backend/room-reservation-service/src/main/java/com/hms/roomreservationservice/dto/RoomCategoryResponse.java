package com.hms.roomreservationservice.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCategoryResponse {

    private Long id;
    private String name;
    private Set<RoomFeatureResponse> features;
}