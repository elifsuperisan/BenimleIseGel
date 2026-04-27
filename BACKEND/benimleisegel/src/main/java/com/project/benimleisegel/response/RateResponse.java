package com.project.benimleisegel.response;

import java.time.LocalDateTime;

public record RateResponse(
        Long id,
        int score,
        String comment,
        UserResponse raterUser,
        UserResponse ratedUser,
        RideResponse ride,
        LocalDateTime createdAt
) {
}
