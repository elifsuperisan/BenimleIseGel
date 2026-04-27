package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.Ride;
import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.enums.RideStatus;
import com.project.benimleisegel.exception.GeneralException;
import com.project.benimleisegel.exception.ResourceNotBelongsToUserException;
import com.project.benimleisegel.exception.ResourceNotFoundException;
import com.project.benimleisegel.mapper.RideMapper;
import com.project.benimleisegel.repository.RideRepository;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.request.CreateRideRequestAsDriver;
import com.project.benimleisegel.request.UpdateRideStatusRequest;
import com.project.benimleisegel.response.RideResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RideMapper rideMapper;

    public RideService(RideRepository rideRepository,
                       UserRepository userRepository,
                       RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.rideMapper = rideMapper;
    }

    //get all available rides
    public List<RideResponse> getAllAvailableRides() {
        return rideRepository.findByDepartTimeAfterAndStatus(LocalDateTime.now(), RideStatus.OPEN)
                .stream()
                .map(rideMapper::mapToRideResponse)
                .toList();
    }

    //get authenticated users rides as driver
    public List<RideResponse> getRidesOfUser(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.getRides()
                .stream()
                .map(rideMapper::mapToRideResponse)
                .toList();
    }

    //get authenticated users rides as guest
    public List<RideResponse> getMyRidesAsGuest(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return user.getRidesAsGuest()
                .stream()
                .map(rideMapper::mapToRideResponse)
                .toList();
    }

    //create ride
    @Transactional
    public RideResponse createRide(CustomUserDetails customUserDetails,
                                   CreateRideRequestAsDriver request) {
        //user kontrolu
        User driver = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //user'a ait vehicle var mi
        if (driver.getVehicle() == null) {
            throw new ResourceNotFoundException("User doesnt have a vehicle");
        }

        Ride ride = rideMapper.mapToRide(request);
        ride.setDriver(driver);
        ride.setVehicle(driver.getVehicle());
        ride.setStatus(RideStatus.OPEN);

        rideRepository.save(ride);

        return rideMapper.mapToRideResponse(ride);
    }

    // update ride status
    @Transactional
    public RideResponse updateRideStatus(CustomUserDetails customUserDetails,
                                         Long id,
                                         UpdateRideStatusRequest request) {
        //user kontrolu
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //ride
        Ride rideToUpdate = rideRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ride not found"));

        //user, bu ride icin driver rolunde mi
        if (!rideToUpdate.getDriver().getId().equals(user.getId())) {
            throw new ResourceNotBelongsToUserException("This ride not belong to you");
        }

        //tamamlanandı veya iptal olarak işaretlenen bir ride'ın status'u değiştirilemez
        if (rideToUpdate.getStatus().equals(RideStatus.COMPLETED)
                || rideToUpdate.getStatus().equals(RideStatus.CANCELED)) {
            throw new GeneralException("Tamamlanan veya iptal edilen yolculuğun durumu güncellenemez");
        }

        //guest iceren bir ride iptal edilemez
        if (rideToUpdate.getGuest() != null
                && request.status().equals(RideStatus.CANCELED)) {
            throw new GeneralException("Misafir içeren bir yolculuk iptal edilemez");
        }

        //guest olmayan ride completed veya ongoing olarak isaretlenemez
        if (rideToUpdate.getGuest() == null) {
            if (request.status().equals(RideStatus.COMPLETED) || request.status().equals(RideStatus.ONGOING)) {
                throw new GeneralException
                        ("misafir barındırmayan yolculuklar Tamamlandı veya Devam Ediyor şeklinde güncellenemez");
            }
        }

        rideToUpdate.setStatus(request.status());
        rideRepository.save(rideToUpdate);

        return rideMapper.mapToRideResponse(rideToUpdate);
    }

}
