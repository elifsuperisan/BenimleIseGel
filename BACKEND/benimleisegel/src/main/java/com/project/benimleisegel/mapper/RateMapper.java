package com.project.benimleisegel.mapper;

import com.project.benimleisegel.entity.Rate;
import com.project.benimleisegel.request.CreateRateRequest;
import com.project.benimleisegel.response.RateResponse;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {

    private final UserMapper userMapper;
    private final RideMapper rideMapper;

    public RateMapper(UserMapper userMapper, RideMapper rideMapper) {
        this.userMapper = userMapper;
        this.rideMapper = rideMapper;
    }

    public Rate mapToRate(CreateRateRequest request) {
        Rate rate = new Rate();
        rate.setScore(request.score());
        rate.setComment(request.comment());
        return rate;
    }

    public RateResponse mapToResponse(Rate rate) {
        RateResponse response = new RateResponse(rate.getId(),
                rate.getScore(),
                rate.getComment(),
                userMapper.mapToUserResponse(rate.getRater()),
                userMapper.mapToUserResponse(rate.getRatedUser()),
                rideMapper.mapToRideResponse(rate.getRide()),
                rate.getCreatedAt());
        return response;
    }
}
