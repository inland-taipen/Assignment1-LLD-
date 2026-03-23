package com.example.snakesandladders;

public abstract class BoardEntity {
    protected final int start;
    protected final int end;

    protected BoardEntity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

