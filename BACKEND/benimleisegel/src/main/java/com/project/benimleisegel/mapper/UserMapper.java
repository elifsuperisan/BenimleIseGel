package com.project.benimleisegel.mapper;

import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.request.CreateUserRequest;
import com.project.benimleisegel.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final VehicleMapper vehicleMapper;

    public UserMapper(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    public User mapToUser(CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());
        return user;
    }

    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        if (user.getVehicle() != null) {
            userResponse.setVehicle(vehicleMapper.mapToVehicleResponse(user.getVehicle()));
        }
        userResponse.setScore(user.getAverageRating());
        return userResponse;
    }

}
