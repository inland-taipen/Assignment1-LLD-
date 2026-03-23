package com.example.snakesandladders;

public enum DifficultyLevel {
    EASY,
    HARD;

    public static DifficultyLevel fromString(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("difficulty_level cannot be null");
        }
        String normalized = raw.trim().toLowerCase();
        return switch (normalized) {
            case "easy" -> EASY;
            case "hard" -> HARD;
            default -> throw new IllegalArgumentException("Unsupported difficulty_level: " + raw);
        };
    }
}

