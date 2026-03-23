package com.example.parkinglot;

public class Slot {
    private final String slotId;
    private final int floorNumber;
    private final VehicleType type;
    private Vehicle parkedVehicle; // null => free

    public Slot(String slotId, int floorNumber, VehicleType type) {
        if (slotId == null || slotId.isBlank()) {
            throw new IllegalArgumentException("slotId cannot be blank");
        }
        if (floorNumber < 1) {
            throw new IllegalArgumentException("floorNumber must be >= 1");
        }
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        this.slotId = slotId.trim();
        this.floorNumber = floorNumber;
        this.type = type;
        this.parkedVehicle = null;
    }

    public String getSlotId() {
        return slotId;
    }

    public VehicleType getType() {
        return type;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public boolean isAvailable() {
        return parkedVehicle == null;
    }

    public void park(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle cannot be null");
        }
        if (vehicle.getType() != type) {
            throw new IllegalArgumentException("Slot type mismatch. Slot=" + type + ", Vehicle=" + vehicle.getType());
        }
        if (!isAvailable()) {
            throw new IllegalStateException("Slot " + slotId + " is already occupied");
        }
        this.parkedVehicle = vehicle;
    }

    public Vehicle unpark() {
        Vehicle v = parkedVehicle;
        parkedVehicle = null;
        return v;
    }
}

