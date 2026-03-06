package com.hms.roomreservationservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_facilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}