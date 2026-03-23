package com.example.snakesandladders;

public class Ladder extends BoardEntity {
    public Ladder(int start, int end) {
        super(start, end);
        if (end <= start) {
            throw new IllegalArgumentException("Ladder end must be > start");
        }
    }
}

