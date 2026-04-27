package com.project.benimleisegel.request;

public record LoginRequest(
        String email,
        String password
) {
}
