package com.project.benimleisegel.mapper;

import com.project.benimleisegel.entity.Vehicle;
import com.project.benimleisegel.request.CreateVehicleRequest;
import com.project.benimleisegel.response.VehicleResponse;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle mapToVehicle(CreateVehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(request.plate());
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        return vehicle;
    }

    public VehicleResponse mapToVehicleResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setPlate(vehicle.getPlate());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel());
        return response;
    }

}
