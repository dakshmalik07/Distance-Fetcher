package com.backend.maps.service;

import com.backend.maps.model.RouteInfo;
import com.backend.maps.reposiotry.RouteInfoRepository;
import com.backend.maps.util.GoogleMapsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DistanceService {

    @Autowired
    private RouteInfoRepository routeInfoRepository;

    @Autowired
    private GoogleMapsClient googleMapsClient;

    public RouteInfo getDistance(String fromPincode, String toPincode) {
       
        Optional<RouteInfo> routeInfoOpt = routeInfoRepository.findByFromPincodeAndToPincode(fromPincode, toPincode);

        if (routeInfoOpt.isPresent()) {
            return routeInfoOpt.get();
        }

      
        RouteInfo routeInfo = googleMapsClient.fetchDistanceData(fromPincode, toPincode);
        routeInfoRepository.save(routeInfo);

        return routeInfo;
    }
}
