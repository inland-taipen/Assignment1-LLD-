package com.example.pen;

/**
 * Simple cap logic.
 * This is the part the pen "has" (aggregation idea).
 */
public class Cap {
    private boolean closed;

    public Cap(boolean initiallyClosed) {
        this.closed = initiallyClosed;
    }

    // start() opens the cap
    public void open() {
        closed = false;
    }

    // close() closes the cap
    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}

