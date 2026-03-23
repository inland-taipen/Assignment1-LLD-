package com.example.snakesandladders;

public class Player {
    private final String name;
    private int position; // 0 means "outside the board"
    private boolean won;

    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("player name cannot be blank");
        }
        this.name = name;
        this.position = 0;
        this.won = false;
    }

    public String getName() {
        return name;
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

