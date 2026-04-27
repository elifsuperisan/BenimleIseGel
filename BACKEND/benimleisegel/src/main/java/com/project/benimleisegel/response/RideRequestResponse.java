package com.project.benimleisegel.response;

import com.project.benimleisegel.enums.RideRequestStatus;

public record RideRequestResponse(
        Long id,
        RideResponse ride,
        UserResponse guest,
        RideRequestStatus status
) {
}
