package com.project.benimleisegel.controller;

import com.project.benimleisegel.request.CreateRateRequest;
import com.project.benimleisegel.response.RateResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import com.project.benimleisegel.service.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    //get my rates as rater
    @GetMapping("/as-rater")
    public ResponseEntity<List<RateResponse>> getMyRatesAsRater(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(rateService.getMyRatesAsRater(customUserDetails));
    }

    //get my rates as rated
    @GetMapping("/as-rated")
    public ResponseEntity<List<RateResponse>> getMyRatesAsRated(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(rateService.getMyRatesAsRated(customUserDetails));
    }

    //create rate
    @PostMapping
    public ResponseEntity<RateResponse> createRate(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                   @RequestBody CreateRateRequest request) {
        return new ResponseEntity<>(rateService.createRate(customUserDetails, request), HttpStatus.CREATED);
    }
}
