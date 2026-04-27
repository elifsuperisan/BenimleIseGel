package com.project.benimleisegel.request;

public record CreateRateRequest(
        int score,
        String comment,
        Long targetUserId,
        Long targetRideId
) {
}
