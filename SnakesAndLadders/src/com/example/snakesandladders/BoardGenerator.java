package com.example.snakesandladders;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BoardGenerator {
    // To match the assignment statement: pieces should not move beyond 100.
    // The app also supports n x n boards, but this constraint is enforced to keep the spec consistent.
    private static final int ASSIGNMENT_MAX_CELL = 100;

    public static Board generate(int n, DifficultyLevel difficulty, Random rng) {
        if (rng == null) {
            throw new IllegalArgumentException("rng cannot be null");
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("difficulty cannot be null");
        }

        if (n < 2) {
            throw new IllegalArgumentException("n must be >= 2");
        }

        int maxCell = n * n;
        if (maxCell > ASSIGNMENT_MAX_CELL) {
            throw new IllegalArgumentException(
                    "For this assignment the board must fit within 1..100 cells. " +
                            "So n must be <= 10 (got n=" + n + ")."
            );
        }

        return generateInternal(n, maxCell, difficulty, rng);
    }

    private static Board generateInternal(int n, int maxCell, DifficultyLevel difficulty, Random rng) {
        // Map for cycle-checking: outgoing edge per source cell.
        // Sources are snake heads and ladder starts (disjoint).
        Map<Integer, Integer> edges = new HashMap<>();

        // Board entities keyed by their start cell (head or ladder-start).
        Map<Integer, BoardEntity> snakesAndLadders = new HashMap<>();

        // Generate snakes first (sources = snake heads)
        int snakeCount = 0;
        int attempts = 0;
        int maxAttempts = 50_000;

        while (snakeCount < n) {
            if (attempts++ > maxAttempts) {
                // Restart generation if it's too constrained for the chosen settings.
                return generateInternal(n, maxCell, difficulty, new Random(rng.nextLong()));
            }

            // Avoid 1 and maxCell as snake-heads so the last cell can be a win-cell.
            int head = randomInt(rng, 2, maxCell - 1);
            if (edges.containsKey(head)) {
                continue; // also prevents collisions with previously created ladders
            }

            int tail = pickSnakeTail(head, difficulty, maxCell, rng);
            if (tail >= head) {
                continue;
            }

            // Ensure no cycles in the directed graph of transitions.
            if (createsCycle(head, tail, edges)) {
                continue;
            }

            snakesAndLadders.put(head, new Snake(head, tail));
            edges.put(head, tail);
            snakeCount++;
        }

        // Generate ladders next (sources = ladder starts). Disallow ladder-start == any snake-head.
        int ladderCount = 0;
        attempts = 0;
        while (ladderCount < n) {
            if (attempts++ > maxAttempts) {
                return generateInternal(n, maxCell, difficulty, new Random(rng.nextLong()));
            }

            int start = randomInt(rng, 1, maxCell - 1);
            if (edges.containsKey(start)) {
                continue;
            }

            int end = pickLadderEnd(start, difficulty, maxCell, rng);
            if (end <= start || end > maxCell) {
                continue;
            }

            if (createsCycle(start, end, edges)) {
                continue;
            }

            snakesAndLadders.put(start, new Ladder(start, end));
            edges.put(start, end);
            ladderCount++;
        }

        return new Board(n, snakesAndLadders);
    }

    private static boolean createsCycle(int source, int destination, Map<Integer, Integer> edges) {
        // Adding source -> destination creates a cycle iff destination can reach source using existing edges.
        int current = destination;

        int safety = 0;
        while (edges.containsKey(current)) {
            if (current == source) {
                return true;
            }
            current = edges.get(current);
            safety++;
            if (safety > edges.size() + 5) {
                return false;
            }
        }
        return false;
    }

    private static int pickSnakeTail(int head, DifficultyLevel difficulty, int maxCell, Random rng) {
        // Derive a difficulty-dependent drop size from board size.
        // easy => drop tends to be smaller
        // hard => drop tends to be larger
        if (difficulty == DifficultyLevel.EASY) {
            int maxDrop = Math.max(1, maxCell / 20); // e.g., 100 => 5
            int drop = 1 + rng.nextInt(Math.max(1, Math.min(maxDrop, head - 1)));
            return head - drop;
        }

        // HARD
        int minDrop = Math.max(1, maxCell / 20); // e.g., 100 => 5
        int maxDrop = head - 1;
        if (maxDrop <= 1) {
            return head - 1;
        }

        int effectiveMinDrop = Math.min(minDrop, maxDrop);
        int dropRange = maxDrop - effectiveMinDrop + 1;
        if (dropRange <= 0) {
            return head - 1;
        }

        int drop = effectiveMinDrop + rng.nextInt(dropRange);
        return head - drop;
    }

    private static int pickLadderEnd(int start, DifficultyLevel difficulty, int maxCell, Random rng) {
        // easy => rise tends to be smaller
        // hard => rise tends to be larger
        if (difficulty == DifficultyLevel.EASY) {
            int maxRise = Math.max(1, maxCell / 20); // e.g., 100 => 5
            int upper = Math.min(maxCell, start + maxRise);
            if (upper <= start) {
                return start + 1;
            }
            return start + 1 + rng.nextInt(upper - (start + 1) + 1);
        }

        // HARD
        int minRise = Math.max(1, maxCell / 20); // e.g., 100 => 5
        int lower = start + minRise;
        if (lower >= maxCell) {
            return maxCell;
        }
        return lower + rng.nextInt(maxCell - lower + 1);
    }

    private static int randomInt(Random rng, int inclusiveMin, int inclusiveMax) {
        if (inclusiveMin > inclusiveMax) {
            throw new IllegalArgumentException("Invalid range: " + inclusiveMin + ".." + inclusiveMax);
        }
        return inclusiveMin + rng.nextInt(inclusiveMax - inclusiveMin + 1);
    }
}

