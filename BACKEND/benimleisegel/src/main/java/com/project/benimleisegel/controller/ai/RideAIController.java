package com.project.benimleisegel.controller.ai;

import com.project.benimleisegel.response.RideSalarySuggestionResponse;
import com.project.benimleisegel.service.ai.RideAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class RideAIController {

    private final RideAIService rideAIService;

    public RideAIController(RideAIService rideAIService) {
        this.rideAIService = rideAIService;
    }

    @GetMapping
    public ResponseEntity<RideSalarySuggestionResponse> getSalarySuggestion(
            @RequestParam double distanceInMeters,
            @RequestParam double durationInSeconds,
            @RequestParam String carBrand,
            @RequestParam String carModel) {
        return ResponseEntity.ok(rideAIService.getSalarySuggestion(
                distanceInMeters, durationInSeconds, carBrand, carModel)
        );
    }

}
