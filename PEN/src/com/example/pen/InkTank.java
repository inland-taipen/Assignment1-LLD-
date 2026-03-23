package com.example.pen;

/**
 * Simple ink storage for the pen.
 * This is the object the Pen "owns" (composition idea).
 */
public class InkTank {
    private final int capacityMl;
    private int inkMl;

    public InkTank(int capacityMl, int initialInkMl) {
        if (capacityMl <= 0) {
            throw new IllegalArgumentException("capacityMl must be > 0");
        }
        if (initialInkMl < 0 || initialInkMl > capacityMl) {
            throw new IllegalArgumentException("initialInkMl must be in [0, capacityMl]");
        }
        this.capacityMl = capacityMl;
        this.inkMl = initialInkMl;
    }

    // Removes ink when the pen writes
    public int consumeInk(int units) {
        if (units < 0) {
            throw new IllegalArgumentException("units must be >= 0");
        }
        int consumed = Math.min(inkMl, units);
        inkMl -= consumed;
        return consumed;
    }

    // Adds ink when we refill from a bottle
    public void addInk(int units) {
        if (units < 0) {
            throw new IllegalArgumentException("units must be >= 0");
        }
        int amountToAdd = Math.min(units, getRemainingCapacityMl());
        inkMl += amountToAdd;
    }

    public int getInkMl() {
        return inkMl;
    }

    public int getRemainingCapacityMl() {
        return capacityMl - inkMl;
    }
}

