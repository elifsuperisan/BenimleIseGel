package com.project.benimleisegel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.benimleisegel.enums.RideStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    @JsonBackReference(value = "user-driver")
    private User driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id")
    @JsonBackReference(value = "user-guest")
    private User guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private String originAddress;

    private String destinationAddress;

    private double distanceInMeters;

    private double durationInSeconds;

    //route fields
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    private LocalDateTime departTime;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @OneToMany(
            mappedBy = "ride",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RideRequest> rideRequests = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rate_id")
    private Rate rate;

    public Ride() {
    }

    public Ride(Long id,
                String title,
                User driver,
                User guest,
                Vehicle vehicle,
                String originAddress,
                String destinationAddress,
                double distanceInMeters,
                double durationInSeconds,
                double originLatitude,
                double originLongitude,
                double destinationLatitude,
                double destinationLongitude,
                LocalDateTime departTime,
                BigDecimal price,
                RideStatus status,
                List<RideRequest> rideRequests,
                Rate rate) {
        this.id = id;
        this.title = title;
        this.driver = driver;
        this.guest = guest;
        this.vehicle = vehicle;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.distanceInMeters = distanceInMeters;
        this.durationInSeconds = durationInSeconds;
        this.originLatitude = originLatitude;
        this.originLongitude = originLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.departTime = departTime;
        this.price = price;
        this.status = status;
        this.rideRequests = rideRequests;
        this.rate = rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public double getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public double getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(double durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public LocalDateTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalDateTime departTime) {
        this.departTime = departTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(List<RideRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
