package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.entity.Vehicle;
import com.project.benimleisegel.exception.ResourceAlreadyExistsException;
import com.project.benimleisegel.exception.ResourceNotFoundException;
import com.project.benimleisegel.mapper.VehicleMapper;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.repository.VehicleRepository;
import com.project.benimleisegel.request.CreateVehicleRequest;
import com.project.benimleisegel.response.VehicleResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository,
                          UserRepository userRepository,
                          VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.vehicleMapper = vehicleMapper;
    }

    //create vehicle
    @Transactional
    public VehicleResponse createVehicle(CustomUserDetails customUserDetails,
                                         CreateVehicleRequest request) {
        //get user
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User with email " + customUserDetails.getUsername() + " not found"));

        //check plate number
        if (vehicleRepository.existsByPlate(request.plate())) {
            throw new ResourceAlreadyExistsException("Plate " + request.plate() + " already exists");
        }

        Vehicle vehicle = vehicleMapper.mapToVehicle(request);

        vehicleRepository.save(vehicle);

        //set user
        user.setVehicle(vehicle);
        userRepository.save(user);

        return vehicleMapper.mapToVehicleResponse(vehicle);
    }

    //delete users vehicle
    @Transactional
    public void deleteUsersVehicle(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        //kullaniciya ait arac kaydi var mi
        if (user.getVehicle() == null) {
            throw new ResourceNotFoundException("User does not have a vehicle");
        }

        //vehicle - user iliskisini kopar
        user.setVehicle(null);
        userRepository.save(user);
    }
}
