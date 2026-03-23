package com.example.snakesandladders;

import java.util.Random;

public class StandardDie implements Die {
    private final Random rng;

    public StandardDie(Random rng) {
        if (rng == null) {
            throw new IllegalArgumentException("rng cannot be null");
        }
        this.rng = rng;
    }

    @Override
    public int roll() {
        // 1..6 inclusive
        return 1 + rng.nextInt(6);
    }
}

