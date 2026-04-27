package com.project.benimleisegel.controller;

import com.project.benimleisegel.response.UserResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import com.project.benimleisegel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //get authenticated user
    @GetMapping
    public ResponseEntity<UserResponse> getAuthenticatedUser
        (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(customUserDetails));
    }

    //delete user
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.deleteUser(customUserDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
