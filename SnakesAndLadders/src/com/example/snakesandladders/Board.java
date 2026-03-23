package com.example.snakesandladders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final int size; // n
    private final int maxCell; // n*n

    // Snake: head -> tail (tail < head)
    private final Map<Integer, Integer> snakeHeadsToTails;

    // Ladder: start -> end (end > start)
    private final Map<Integer, Integer> ladderStartsToEnds;

    public Board(
            int size,
            Map<Integer, Integer> snakeHeadsToTails,
            Map<Integer, Integer> ladderStartsToEnds
    ) {
        if (size < 2) {
            throw new IllegalArgumentException("Board size must be >= 2");
        }
        this.size = size;
        this.maxCell = size * size;

        this.snakeHeadsToTails = Collections.unmodifiableMap(new HashMap<>(snakeHeadsToTails));
        this.ladderStartsToEnds = Collections.unmodifiableMap(new HashMap<>(ladderStartsToEnds));
    }

    public int getSize() {
        return size;
    }

    public int getMaxCell() {
        return maxCell;
    }

    public Map<Integer, Integer> getSnakes() {
        return snakeHeadsToTails;
    }

    public Map<Integer, Integer> getLadders() {
        return ladderStartsToEnds;
    }

    public int applyTransitions(int position) {
        // Re-apply transitions until landing on a cell that is neither a snake head nor a ladder start.
        // Since we prevent cycles during generation, this loop must terminate.
        int current = position;
        int safetyLimit = (2 * size) + 10;

        for (int i = 0; i < safetyLimit; i++) {
            Integer tail = snakeHeadsToTails.get(current);
            if (tail != null) {
                current = tail;
                continue;
            }

            Integer ladderEnd = ladderStartsToEnds.get(current);
            if (ladderEnd != null) {
                current = ladderEnd;
                continue;
            }

            break;
        }

        return current;
    }
}

