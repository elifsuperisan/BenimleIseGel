package com.project.benimleisegel.mapper;

import com.project.benimleisegel.entity.RideRequest;
import com.project.benimleisegel.response.RideRequestResponse;
import org.springframework.stereotype.Component;

@Component
public class RideRequestMapper {

    private final RideMapper rideMapper;
    private final UserMapper userMapper;

    public RideRequestMapper(RideMapper rideMapper,
                             UserMapper userMapper) {
        this.rideMapper = rideMapper;
        this.userMapper = userMapper;
    }

    public RideRequestResponse mapToResponse(RideRequest rideRequest) {
        return new RideRequestResponse(rideRequest.getId(),
                rideMapper.mapToRideResponse(rideRequest.getRide()),
                userMapper.mapToUserResponse(rideRequest.getGuest()),
                rideRequest.getStatus());
    }

}
