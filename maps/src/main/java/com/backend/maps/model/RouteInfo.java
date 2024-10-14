package com.backend.maps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromPincode;
    private String toPincode;
    private String distance;
    private String duration;
    @Column(columnDefinition = "TEXT")
    private String routeDetails;


}
