package com.project.benimleisegel.mapper;

import com.project.benimleisegel.entity.Ride;
import com.project.benimleisegel.request.CreateRideRequestAsDriver;
import com.project.benimleisegel.response.RideResponse;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    private final UserMapper userMapper;

    public RideMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Ride mapToRide(CreateRideRequestAsDriver request) {
        Ride ride = new Ride();
        ride.setTitle(request.title());
        ride.setOriginAddress(request.originAddress());
        ride.setDestinationAddress(request.destinationAddress());
        ride.setDistanceInMeters(request.distanceInMeters());
        ride.setDurationInSeconds(request.durationInSeconds());
        ride.setOriginLatitude(request.originLatitude());
        ride.setOriginLongitude(request.originLongitude());
        ride.setDestinationLatitude(request.destinationLatitude());
        ride.setDestinationLongitude(request.destinationLongitude());
        ride.setDepartTime(request.departTime());
        ride.setPrice(request.price());
        return ride;
    }

    public RideResponse mapToRideResponse(Ride ride) {
        RideResponse rideResponse = new RideResponse(
                ride.getId(),
                ride.getTitle(),
                userMapper.mapToUserResponse(ride.getDriver()),
                ride.getOriginAddress(),
                ride.getDestinationAddress(),
                ride.getDistanceInMeters(),
                ride.getDurationInSeconds(),
                ride.getOriginLatitude(),
                ride.getOriginLongitude(),
                ride.getDestinationLatitude(),
                ride.getDestinationLongitude(),
                ride.getDepartTime(),
                ride.getPrice(),
                ride.getStatus()
        );
        return rideResponse;
    }

}
