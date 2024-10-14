package com.backend.maps.service;


import com.backend.maps.model.RouteInfo;
import com.backend.maps.reposiotry.RouteInfoRepository;
import com.backend.maps.util.GoogleMapsClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;

    @Mock
    private RouteInfoRepository routeInfoRepository;

    @Mock
    private GoogleMapsClient googleMapsClient;

    private RouteInfo routeInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        routeInfo = new RouteInfo(null, "141106", "110060", "300 km", "4 hours", "details...");
    }

    @Test
    void getDistance_ShouldReturnCachedData_WhenDataIsPresentInDB() {
        when(routeInfoRepository.findByFromPincodeAndToPincode("141106", "110060")).thenReturn(Optional.of(routeInfo));

        RouteInfo result = distanceService.getDistance("141106", "110060");

        verify(googleMapsClient, never()).fetchDistanceData(anyString(), anyString());
        assertEquals(routeInfo, result);
    }

    @Test
    void getDistance_ShouldCallGoogleMapsAPI_WhenDataIsNotInDB() {
        when(routeInfoRepository.findByFromPincodeAndToPincode("141106", "110060")).thenReturn(Optional.empty());
        when(googleMapsClient.fetchDistanceData("141106", "110060")).thenReturn(routeInfo);

        RouteInfo result = distanceService.getDistance("141106", "110060");

        verify(googleMapsClient, times(1)).fetchDistanceData("141106", "110060");
        assertEquals(routeInfo, result);
    }
}
