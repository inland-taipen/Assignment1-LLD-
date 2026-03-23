package com.example.pen;

/**
 * Simple ink model.
 * Pen color/type can change every time we refill using a different Bottle.
 */
public class Ink {
    private final String type; // e.g., ball, gel, fountain (student-friendly)
    private final String color; // e.g., Blue, Red

    public Ink(String type, String color) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("type must not be empty");
        }
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("color must not be empty");
        }
        this.type = type.trim();
        this.color = color.trim();
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }
}

