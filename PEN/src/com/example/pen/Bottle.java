package com.example.pen;

/**
 * External bottle used to refill the pen.
 * The Pen does not own this object (so it is outside).
 */
public class Bottle {
    private int remainingMl;
    private Ink ink; // bottle carries ink with type + color

    public Bottle(Ink ink, int remainingMl) {
        if (ink == null) {
            throw new IllegalArgumentException("ink must not be null");
        }
        if (remainingMl < 0) {
            throw new IllegalArgumentException("remainingMl must be >= 0");
        }
        this.ink = ink;
        this.remainingMl = remainingMl;
    }

    // Pour ink into the pen's tank. Returns how much ink actually poured.
    public int pourInto(InkTank tank) {
        if (tank == null) {
            throw new IllegalArgumentException("tank must not be null");
        }

        if (remainingMl == 0) {
            return 0;
        }

        int canPour = Math.min(remainingMl, tank.getRemainingCapacityMl());
        tank.addInk(canPour);
        remainingMl -= canPour;
        return canPour;
    }

    public Ink getInk() {
        return ink;
    }

    public int getRemainingMl() {
        return remainingMl;
    }
}

