package com.example.parkinglot;

public class Ticket {
    private final String token;
    private final VehicleType vehicleType;
    private final Slot slot;
    private final long entryTimeMillis;

    public Ticket(String token, VehicleType vehicleType, Slot slot, long entryTimeMillis) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token cannot be blank");
        }
        if (vehicleType == null) {
            throw new IllegalArgumentException("vehicleType cannot be null");
        }
        if (slot == null) {
            throw new IllegalArgumentException("slot cannot be null");
        }
        this.token = token.trim();
        this.vehicleType = vehicleType;
        this.slot = slot;
        this.entryTimeMillis = entryTimeMillis;
    }

    public String getToken() {
        return token;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Slot getSlot() {
        return slot;
    }

    public long getEntryTimeMillis() {
        return entryTimeMillis;
    }
}

