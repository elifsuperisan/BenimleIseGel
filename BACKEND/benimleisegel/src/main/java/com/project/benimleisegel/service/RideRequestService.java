package com.project.benimleisegel.service;

import com.project.benimleisegel.entity.Ride;
import com.project.benimleisegel.entity.RideRequest;
import com.project.benimleisegel.entity.User;
import com.project.benimleisegel.enums.RideRequestStatus;
import com.project.benimleisegel.enums.RideStatus;
import com.project.benimleisegel.exception.GeneralException;
import com.project.benimleisegel.exception.ResourceNotBelongsToUserException;
import com.project.benimleisegel.exception.ResourceNotFoundException;
import com.project.benimleisegel.mapper.RideRequestMapper;
import com.project.benimleisegel.repository.RideRepository;
import com.project.benimleisegel.repository.RideRequestRepository;
import com.project.benimleisegel.repository.UserRepository;
import com.project.benimleisegel.request.CreateRideRequestRequest;
import com.project.benimleisegel.request.UpdateRideRequestStatusRequest;
import com.project.benimleisegel.response.RideRequestResponse;
import com.project.benimleisegel.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RideRequestMapper rideRequestMapper;

    public RideRequestService(RideRequestRepository rideRequestRepository,
                              RideRepository rideRepository,
                              UserRepository userRepository,
                              RideRequestMapper rideRequestMapper) {
        this.rideRequestRepository = rideRequestRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.rideRequestMapper = rideRequestMapper;
    }

    //get auth. users ride requests
    public List<RideRequestResponse> getMyRideRequests(CustomUserDetails customUserDetails) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return user.getRideRequests()
                .stream()
                .map(rideRequestMapper::mapToResponse)
                .toList();
    }

    //get ride requests of my ride
    public List<RideRequestResponse> getRideRequestsOfMyRides
        (CustomUserDetails customUserDetails, Long id) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Ride ride = rideRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("ride not found"));

        //is ride belongs to user
        if (!ride.getDriver().getId().equals(user.getId())) {
            throw new ResourceNotBelongsToUserException("this ride not belongs to you");
        }

        return ride.getRideRequests()
                .stream()
                .map(rideRequestMapper::mapToResponse)
                .toList();
    }

    //create ride request
    @Transactional
    public RideRequestResponse createRideRequest(CustomUserDetails customUserDetails,
                                                 CreateRideRequestRequest request) {
        Ride ride = rideRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("ride not found"));

        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        //Bu ride user'ın kendisine mi ait
        if (ride.getDriver().getId().equals(user.getId())) {
            throw new GeneralException("Kendi yolculuğunuza talepte bulunamazsınız");
        }

        //kullanıcı bu ride'a daha önce request'de bulunmus mu
        for (RideRequest rideRequest : ride.getRideRequests()) {
            if (rideRequest.getGuest().getId().equals(user.getId())) {
                throw new GeneralException("daha önce bu yolculuğa talepte bulunmuşsunuz");
            }
        }

        RideRequest rideRequest = new RideRequest();
        rideRequest.setRide(ride);
        rideRequest.setGuest(user);
        rideRequest.setStatus(RideRequestStatus.PENDING);

        rideRequestRepository.save(rideRequest);

        return rideRequestMapper.mapToResponse(rideRequest);
    }

    //update ride request status of my ride
    @Transactional
    public RideRequestResponse updateRideRequestStatus(CustomUserDetails customUserDetails,
                                                       Long id,
                                                       UpdateRideRequestStatusRequest request) {
        User user = userRepository.findByEmail(customUserDetails.getUsername())
                .orElseThrow(()-> new ResourceNotFoundException("user not found"));

        RideRequest rideRequest = rideRequestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("ride request not found"));

        Ride ride = rideRequest.getRide();

        //ride request'in yapildiği ride user'a mı ait
        if (!rideRequest.getRide().getDriver().getId().equals(user.getId())) {
            throw new GeneralException("bu ride size ait degil");
        }

        //ride completed durumunduysa taleplerin status'u guncellenemez
        if (ride.getStatus().equals(RideStatus.COMPLETED)) {
            throw new GeneralException("Tamamlanmış yolculuğun talepleri güncellenemez");
        }

        //kabul edilmiş veya reddedilmiş talepler güncellenmemeli
        if (rideRequest.getStatus().equals(RideRequestStatus.ACCEPTED)
            || rideRequest.getStatus().equals(RideRequestStatus.REJECTED)) {
            throw new GeneralException("Daha önceden kabul edilmiş veya reddedilmiş taleplerin durumu güncellenemez");
        }

        rideRequest.setStatus(request.status());
        rideRequestRepository.save(rideRequest);

        //yeni status'a gore aksiyon al
        if (request.status().equals(RideRequestStatus.ACCEPTED)) {
            //ride'a ait guest'i ekle
            ride.setGuest(rideRequest.getGuest());
            rideRepository.save(ride);
        } else if(request.status().equals(RideRequestStatus.REJECTED)) {
            //eğer daha önceden kabul edilmişse guest user'ı ride'dan cikar
            if (ride.getGuest() != null) {
                ride.setGuest(null);
                rideRepository.save(ride);
            }
        }

        return rideRequestMapper.mapToResponse(rideRequest);
    }

}
