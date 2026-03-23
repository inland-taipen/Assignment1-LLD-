package com.example.snakesandladders;

public class Player {
    private final int id;
    private int position; // 0 means "outside the board"
    private boolean won;

    public Player(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("player id must be positive");
        }
        this.id = id;
        this.position = 0;
        this.won = false;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean hasWon() {
        return won;
    }

    public void markWon() {
        this.won = true;
    }
}

