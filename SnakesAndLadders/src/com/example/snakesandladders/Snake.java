package com.example.snakesandladders;

public class Snake extends BoardEntity {
    public Snake(int head, int tail) {
        super(head, tail);
        if (head <= tail) {
            throw new IllegalArgumentException("Snake head must be > tail");
        }
    }
}

