package com.project.benimleisegel.entity;

import com.project.benimleisegel.enums.RideRequestStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "ride_requests")
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    private User guest;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus status;

    public RideRequest() {
    }

    public RideRequest(Long id,
                       Ride ride,
                       User guest,
                       RideRequestStatus status) {
        this.id = id;
        this.ride = ride;
        this.guest = guest;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }
}
