package com.project.benimleisegel.request;

import com.project.benimleisegel.enums.RideStatus;

public record UpdateRideStatusRequest(
        RideStatus status
) {
}
