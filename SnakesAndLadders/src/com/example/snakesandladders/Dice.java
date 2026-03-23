package com.example.snakesandladders;

import java.util.Random;

/**
 * Dice for rolling 1..6 (inclusive).
 */
public class Dice {
    private final int minValue;
    private final int maxValue;
    private final Random rng;

    public Dice(int minValue, int maxValue, Random rng) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("minValue must be <= maxValue");
        }
        if (rng == null) {
            throw new IllegalArgumentException("rng cannot be null");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.rng = rng;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int roll() {
        // Inclusive range
        return minValue + rng.nextInt(maxValue - minValue + 1);
    }
}

