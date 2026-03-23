package com.example.parkinglot;

public class PaymentReceipt {
    private final String token;
    private final long minutes;
    private final double amount;

    public PaymentReceipt(String token, long minutes, double amount) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token cannot be blank");
        }
        if (minutes < 0) {
            throw new IllegalArgumentException("minutes cannot be negative");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative");
        }
        this.token = token.trim();
        this.minutes = minutes;
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public long getMinutes() {
        return minutes;
    }

    public double getAmount() {
        return amount;
    }
}

