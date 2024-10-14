package com.backend.maps.controller;

import com.backend.maps.model.RouteInfo;
import com.backend.maps.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/distance")
public class DistanceController {

    @Autowired
    private DistanceService distanceService;



    @GetMapping("/between")
    public ResponseEntity<RouteInfo> getDistance(
            @RequestParam String fromPincode, @RequestParam String toPincode) {

        RouteInfo routeInfo = distanceService.getDistance(fromPincode, toPincode);
        return ResponseEntity.ok(routeInfo);
    }
}
