package com.project.benimleisegel.controller;

import com.project.benimleisegel.request.CreateRideRequestAsDriver;
import com.project.benimleisegel.request.UpdateRideStatusRequest;
import com.project.benimleisegel.response.RideResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import com.project.benimleisegel.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    //get all available rides
    @GetMapping("/available")
    public ResponseEntity<List<RideResponse>> getAllAvailableRides() {
        return ResponseEntity.ok(rideService.getAllAvailableRides());
    }

    //get users rides as driver
    @GetMapping
    public ResponseEntity<List<RideResponse>> getRidesOfUser
        (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(rideService.getRidesOfUser(customUserDetails));
    }

    //get users rides as guest
    @GetMapping("/as-guest")
    public ResponseEntity<List<RideResponse>> getRidesOfUserAsGuest
        (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(rideService.getMyRidesAsGuest(customUserDetails));
    }

    //create ride
    @PostMapping
    public ResponseEntity<RideResponse> createRide(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @RequestBody CreateRideRequestAsDriver request) {
        return new ResponseEntity<>(rideService.createRide(customUserDetails, request), HttpStatus.CREATED);
    }

    //update ride status
    @PutMapping("/{id}")
    public ResponseEntity<RideResponse> updateRideStatus(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                         @PathVariable("id") Long id,
                                                         @RequestBody UpdateRideStatusRequest request) {
        return new ResponseEntity<>(rideService.updateRideStatus(customUserDetails, id, request), HttpStatus.OK);
    }
}
