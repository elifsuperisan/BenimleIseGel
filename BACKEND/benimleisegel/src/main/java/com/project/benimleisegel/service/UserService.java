package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.exception.ResourceNotFoundException;
import com.project.benimleisegel.mapper.UserMapper;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.response.UserResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //get user by id
    public UserResponse getUserById(Long id) {
        User user =  userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found")
        );

        return userMapper.mapToUserResponse(user);
    }

    //get authenticated user
    public UserResponse getAuthenticatedUser(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException
                        ("User with email " + customUserDetails.getUsername() + " not found"));

        return userMapper.mapToUserResponse(user);
    }

    //delete user
    @Transactional
    public void deleteUser(CustomUserDetails customUserDetails) {
        //User
        User userToDelete = userRepository.findByEmail(customUserDetails.getUsername())
                        .orElseThrow(()-> new ResourceNotFoundException
                                ("User with email " + customUserDetails.getUsername() + " not found"));

        userRepository.delete(userToDelete);
    }

}
