package com.project.benimleisegel.controller;

import com.project.benimleisegel.request.CreateRideRequestRequest;
import com.project.benimleisegel.request.UpdateRideRequestStatusRequest;
import com.project.benimleisegel.response.RideRequestResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import com.project.benimleisegel.service.RideRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ride-requests")
public class RideRequestController {

    private final RideRequestService rideRequestService;

    public RideRequestController(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    //get auth. users ride requests
    @GetMapping
    public ResponseEntity<List<RideRequestResponse>> getMyRideRequests(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(rideRequestService.getMyRideRequests(customUserDetails));
    }

    //get ride requests of my ride
    @GetMapping("/{id}")
    public ResponseEntity<List<RideRequestResponse>>getRideRequestsOfMyRides(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(rideRequestService.getRideRequestsOfMyRides(customUserDetails, id));
    }

    //create ride request
    @PostMapping
    public ResponseEntity<RideRequestResponse> createRideRequest(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreateRideRequestRequest request) {
        return new ResponseEntity<>
                (rideRequestService.createRideRequest(customUserDetails, request), HttpStatus.CREATED);

    }

    //update ride request status of my ride
    @PutMapping("/{id}")
    public ResponseEntity<RideRequestResponse> updateRideRequestStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("id") Long id,
            @RequestBody UpdateRideRequestStatusRequest request) {
        return ResponseEntity.ok(rideRequestService.updateRideRequestStatus(customUserDetails, id, request));
    }
}
