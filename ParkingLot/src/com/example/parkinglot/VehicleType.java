package com.example.parkinglot;

public enum VehicleType {
    SMALL,
    MEDIUM,
    LARGE;

    public static VehicleType fromString(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        String normalized = raw.trim().toUpperCase();
        return switch (normalized) {
            case "SMALL" -> SMALL;
            case "MEDIUM" -> MEDIUM;
            case "LARGE" -> LARGE;
            default -> throw new IllegalArgumentException("Unsupported vehicle type: " + raw);
        };
    }
}

