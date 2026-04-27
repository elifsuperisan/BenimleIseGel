package com.project.benimleisegel.controller;

import com.project.benimleisegel.request.CreateVehicleRequest;
import com.project.benimleisegel.response.VehicleResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import com.project.benimleisegel.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    //create vehicle
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                         @RequestBody CreateVehicleRequest request) {
        return new ResponseEntity<>(vehicleService.createVehicle(customUserDetails, request), HttpStatus.CREATED);
    }

    //delete users vehicle
    @DeleteMapping
    public ResponseEntity<String> deleteVehicle(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        vehicleService.deleteUsersVehicle(customUserDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
