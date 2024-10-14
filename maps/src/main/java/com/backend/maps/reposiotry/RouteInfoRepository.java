package com.backend.maps.reposiotry;

import com.backend.maps.model.RouteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteInfoRepository extends JpaRepository<RouteInfo, Long> {
    Optional<RouteInfo> findByFromPincodeAndToPincode(String fromPincode, String toPincode);
}
