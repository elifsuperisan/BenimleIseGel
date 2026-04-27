package com.project.benimleisegel.response;

import com.project.benimleisegel.enums.RideStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponse(
        Long id,
        String title,
        UserResponse driver,
        String originAddress,
        String destinationAddress,
        double distanceInMeters,
        double durationInSeconds,
        double originLatitude,
        double originLongitude,
        double destinationLatitude,
        double destinationLongitude,
        LocalDateTime departTime,
        BigDecimal price,
        RideStatus status
) {
}
