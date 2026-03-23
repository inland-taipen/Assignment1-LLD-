package com.example.parkinglot;

public class Vehicle {
    private final String vehicleId;
    private final VehicleType type;

    public Vehicle(String vehicleId, VehicleType type) {
        if (vehicleId == null || vehicleId.isBlank()) {
            throw new IllegalArgumentException("vehicleId cannot be blank");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        this.vehicleId = vehicleId.trim();
        this.type = type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleType getType() {
        return type;
    }
}

