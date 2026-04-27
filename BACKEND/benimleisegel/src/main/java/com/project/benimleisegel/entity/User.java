package com.project.benimleisegel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-driver")
    private List<Ride> rides = new ArrayList<>();

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-guest")
    private List<Ride> ridesAsGuest = new ArrayList<>();

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RideRequest> rideRequests = new ArrayList<>();

    @OneToMany(mappedBy = "rater", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "rater-ratings")
    private List<Rate> givenRatings = new ArrayList<>();

    @OneToMany(mappedBy = "ratedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "rated-ratings")
    private List<Rate> receivedRatings = new ArrayList<>();

    public User() {
    }

    public User(Long id,
                String email,
                String password,
                String firstName,
                String lastName,
                String phone,
                Vehicle vehicle,
                List<Ride> rides,
                List<Ride> ridesAsGuest,
                List<RideRequest> rideRequests,
                List<Rate> givenRatings,
                List<Rate> receivedRatings) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.vehicle = vehicle;
        this.ridesAsGuest = ridesAsGuest;
        this.rides = rides;
        this.rideRequests = rideRequests;
        this.givenRatings = givenRatings;
        this.receivedRatings = receivedRatings;
    }

    @Transient
    public double getAverageRating() {
        if (receivedRatings.isEmpty()) return 0;
        return receivedRatings.stream()
                .mapToInt(Rate::getScore)
                .average()
                .orElse(0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(List<RideRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }

    public List<Rate> getGivenRatings() {
        return givenRatings;
    }

    public void setGivenRatings(List<Rate> givenRatings) {
        this.givenRatings = givenRatings;
    }

    public List<Rate> getReceivedRatings() {
        return receivedRatings;
    }

    public void setReceivedRatings(List<Rate> receivedRatings) {
        this.receivedRatings = receivedRatings;
    }

    public List<Ride> getRidesAsGuest() {
        return ridesAsGuest;
    }

    public void setRidesAsGuest(List<Ride> ridesAsGuest) {
        this.ridesAsGuest = ridesAsGuest;
    }
}
