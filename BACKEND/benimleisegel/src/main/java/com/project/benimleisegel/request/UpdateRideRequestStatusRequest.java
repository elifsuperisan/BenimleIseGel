package com.project.benimleisegel.request;

import com.project.benimleisegel.enums.RideRequestStatus;

public record UpdateRideRequestStatusRequest(
        RideRequestStatus status
) {
}
