package com.example.pen;

public class Pen {
    private static final int INK_PER_CHAR = 1;

    private final InkTank tank;

    private final Cap cap;

    private Ink currentInk;

    private boolean started;

    public Pen(InkTank tank, Cap cap) {
        if (tank == null) {
            throw new IllegalArgumentException("tank must not be null");
        }
        this.tank = tank;
        this.cap = cap;
        this.started = false;
        this.currentInk = null;
    }

    // Pen without cap (optional usage for the "with/without cap" messages requirement).
    public Pen(InkTank tank) {
        this(tank, null);
    }

    public void start() {
        if (cap != null) {
            cap.open();
        }
        started = true;
    }

    public String write(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        if (!started) {
            if (cap == null) {
                throw new IllegalStateException("Click pen is not clicked yet. Click(start) first.");
            }
            throw new IllegalStateException("Pen is not started");
        }
        if (text.isEmpty()) return "";
        if (cap != null && cap.isClosed()) {
            throw new IllegalStateException("Cap is closed. Open it to write.");
        }

        // No partial writing: must have enough ink for the whole text.
        int needed = text.length() * INK_PER_CHAR;
        if (tank.getInkMl() < needed) throw new IllegalStateException("Out of ink. Refill first.");

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            tank.consumeInk(INK_PER_CHAR);
            out.append(text.charAt(i));
        }
        return out.toString();
    }

    public void close() {
        if (cap != null) {
            cap.close();
        }
        started = false;
    }

    // refill() gets ink from an external bottle into the tank.
    public int refill(Bottle bottle) {
        if (bottle == null) {
            throw new IllegalArgumentException("bottle must not be null");
        }
        this.currentInk = bottle.getInk();
        return bottle.pourInto(tank);
    }

    public int getInkMl() {
        return tank.getInkMl();
    }

    public String getInkColor() {
        return currentInk == null ? "none" : currentInk.getColor();
    }

    public String getInkType() {
        return currentInk == null ? "none" : currentInk.getType();
    }
}

