package com.backend.maps.controller;

import com.backend.maps.model.RouteInfo;
import com.backend.maps.service.DistanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DistanceControllerTest {

    @InjectMocks
    private DistanceController distanceController;

    @Mock
    private DistanceService distanceService;

    private RouteInfo routeInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        routeInfo = new RouteInfo(null, "141106", "110060", "300 km", "4 hours", "details...");
    }

    @Test
    void getDistance_ShouldReturnDistanceAndDuration() {
        when(distanceService.getDistance("141106", "110060")).thenReturn(routeInfo);

        ResponseEntity<RouteInfo> response = distanceController.getDistance("141106", "110060");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(routeInfo, response.getBody());
    }
}
