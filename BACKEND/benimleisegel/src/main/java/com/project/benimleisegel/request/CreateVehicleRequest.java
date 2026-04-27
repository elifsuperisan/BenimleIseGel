package com.project.benimleisegel.request;

public record CreateVehicleRequest(
        String plate,
        String brand,
        String model
) {
}
