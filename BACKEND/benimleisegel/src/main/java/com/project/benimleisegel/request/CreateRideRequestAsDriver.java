package com.project.benimleisegel.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRideRequestAsDriver(
        String title,
        String originAddress,
        String destinationAddress,
        double distanceInMeters,
        double durationInSeconds,
        double originLatitude,
        double originLongitude,
        double destinationLatitude,
        double destinationLongitude,
        LocalDateTime departTime,
        BigDecimal price
) {
}
