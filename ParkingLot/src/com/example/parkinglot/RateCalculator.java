package com.example.parkinglot;

import java.util.Map;

public class RateCalculator {
    private final Map<VehicleType, Double> ratePerMinute;

    public RateCalculator(Map<VehicleType, Double> ratePerMinute) {
        if (ratePerMinute == null) {
            throw new IllegalArgumentException("ratePerMinute cannot be null");
        }
        this.ratePerMinute = ratePerMinute;
    }

    public double calculate(VehicleType type, long entryTimeMillis, long exitTimeMillis) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (exitTimeMillis < entryTimeMillis) {
            throw new IllegalArgumentException("exitTimeMillis must be >= entryTimeMillis");
        }

        long durationMillis = exitTimeMillis - entryTimeMillis;
        long minutes = Math.max(1, (long) Math.ceil(durationMillis / 60_000.0));

        Double rate = ratePerMinute.get(type);
        if (rate == null) {
            throw new IllegalStateException("No rate configured for type: " + type);
        }
        return minutes * rate;
    }
}

