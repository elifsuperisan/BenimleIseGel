package com.project.benimleisegel.request;

public record SignupRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {
}
