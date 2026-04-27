package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.exception.ResourceAlreadyExistsException;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.request.LoginRequest;
import com.project.benimleisegel.request.SignupRequest;
import com.project.benimleisegel.response.JwtAuthResponse;
import com.project.benimleisegel.response.SignedUpResponse;
import com.project.benimleisegel.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //login
    public JwtAuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        //generate token
        String token = jwtTokenProvider.generateToken(authentication);

        //set authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        return response;
    }

    //signup
    @Transactional
    public SignedUpResponse signup(SignupRequest request) {
        //check email
        if (userRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        //check phone number
        if (userRepository.existsByPhone(request.phone())) {
            throw new ResourceAlreadyExistsException("Phone already exists");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhone(request.phone());

        userRepository.save(user);

        return new SignedUpResponse("success");
    }

}
