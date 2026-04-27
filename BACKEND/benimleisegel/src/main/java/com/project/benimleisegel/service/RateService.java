package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.Rate;
import com.project.benimleisegel.entity.Ride;
import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.enums.RideStatus;
import com.project.benimleisegel.exception.GeneralException;
import com.project.benimleisegel.exception.ResourceAlreadyExistsException;
import com.project.benimleisegel.exception.ResourceNotFoundException;
import com.project.benimleisegel.mapper.RateMapper;
import com.project.benimleisegel.repository.RateRepository;
import com.project.benimleisegel.repository.RideRepository;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.request.CreateRateRequest;
import com.project.benimleisegel.response.RateResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RateService {

    private final RateRepository rateRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final RateMapper rateMapper;

    public RateService(RateRepository rateRepository,
                       UserRepository userRepository,
                       RideRepository rideRepository,
                       RateMapper rateMapper) {
        this.rateRepository = rateRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.rateMapper = rateMapper;
    }

    //get my rates as rater
    public List<RateResponse> getMyRatesAsRater(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return user.getGivenRatings()
                .stream()
                .map(rateMapper::mapToResponse)
                .toList();
    }

    //get my rates as rated
    public List<RateResponse> getMyRatesAsRated(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        return user.getReceivedRatings()
                .stream()
                .map(rateMapper::mapToResponse)
                .toList();
    }

    //create rate
    @Transactional
    public RateResponse createRate(CustomUserDetails customUserDetails,
                                   CreateRateRequest request) {

        User reviewer = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User targetUser = userRepository.findById(request.targetUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //bir kullanıcı kendini yorumlayamamalı
        if (reviewer.getId().equals(targetUser.getId())) {
            throw new GeneralException("Kendine yorum yapamazsın");
        }

        Ride targetRide = rideRepository.findById(request.targetRideId())
                .orElseThrow(()-> new ResourceNotFoundException("Ride not found"));

        //ride daha önceden yorumlanmış mı
        if (targetRide.getRate() != null) {
            throw new ResourceAlreadyExistsException("Bu yolculuk daha önce puanlanmış");
        }

        //ride competed olarak işaretlenmiş mi
        if (!targetRide.getStatus().equals(RideStatus.COMPLETED)) {
            throw new GeneralException("Yolculuk tamamlanmadan yorum gönderilemez");
        }

        Rate rateToCreate = rateMapper.mapToRate(request);
        rateToCreate.setRater(reviewer);
        rateToCreate.setRatedUser(targetUser);
        rateToCreate.setRide(targetRide);

        Rate createdRate = rateRepository.save(rateToCreate);

        targetRide.setRate(createdRate);
        rideRepository.save(targetRide);

        return rateMapper.mapToResponse(createdRate);
    }

}
