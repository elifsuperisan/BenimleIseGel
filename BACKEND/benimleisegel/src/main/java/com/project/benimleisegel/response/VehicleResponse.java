package com.project.benimleisegel.response;

public class VehicleResponse {

    private Long id;
    private String plate;
    private String brand;
    private String model;

    public VehicleResponse() {
    }

    public VehicleResponse(Long id,
                           String plate,
                           String brand,
                           String model) {
        this.id = id;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
