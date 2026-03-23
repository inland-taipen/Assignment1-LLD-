package com.example.snakesandladders;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class Board {
    private final int size; // n
    private final int maxCell; // n*n

    // key = start cell (snake head or ladder start)
    // value = entity defining the transition to its end cell
    private final Map<Integer, BoardEntity> snakesAndLadders;

    public Board(
            int size,
            Map<Integer, BoardEntity> snakesAndLadders
    ) {
        if (size < 2) {
            throw new IllegalArgumentException("Board size must be >= 2");
        }
        this.size = size;
        this.maxCell = size * size;

        this.snakesAndLadders = Collections.unmodifiableMap(new HashMap<>(snakesAndLadders));
    }

    public int getSize() {
        return size;
    }

    public int getMaxCell() {
        return maxCell;
    }

    public Map<Integer, BoardEntity> getSnakesAndLadders() {
        return snakesAndLadders;
    }

    public int applyTransitions(int position) {
        // Apply transitions after landing. Keep applying if a transition lands on another start cell.
        // Generation ensures the directed graph is acyclic, so this will terminate.
        int current = position;
        int safetyLimit = (2 * size) + 10; // defensive bound

        for (int i = 0; i < safetyLimit; i++) {
            BoardEntity entity = snakesAndLadders.get(current);
            if (entity == null) {
                return current;
            }
            current = entity.getEnd();
        }
        return current;
    }
}

