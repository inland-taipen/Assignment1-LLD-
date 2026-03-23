package com.example.snakesandladders;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Board board;
    private final Die die;
    private final List<Player> players;
    private final int targetWinnerCount; // game ends when at least this many winners exist

    public Game(Board board, Die die, int playersCount, int targetWinnerCount) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (die == null) {
            throw new IllegalArgumentException("die cannot be null");
        }
        if (playersCount < 2) {
            throw new IllegalArgumentException("playersCount must be >= 2");
        }
        if (targetWinnerCount < 2) {
            throw new IllegalArgumentException("targetWinnerCount must be >= 2");
        }

        this.board = board;
        this.die = die;
        this.players = new ArrayList<>();
        for (int i = 1; i <= playersCount; i++) {
            players.add(new Player(i));
        }
        this.targetWinnerCount = targetWinnerCount;
    }

    public void play() {
        System.out.println("Board size: " + board.getSize() + "x" + board.getSize());
        System.out.println("Board last cell: " + board.getMaxCell());
        System.out.println();
        System.out.println("Snakes (head -> tail): " + board.getSnakes());
        System.out.println("Ladders (start -> end): " + board.getLadders());
        System.out.println();

        int winners = 0;
        while (winners < targetWinnerCount) {
            for (Player p : players) {
                if (p.hasWon()) {
                    continue;
                }

                int roll = die.roll(); // 1..6
                int from = p.getPosition();
                int proposed = from + roll;

                // Spec note: don't move beyond 100.
                int moveLimit = Math.min(board.getMaxCell(), 100);
                if (proposed > moveLimit) {
                    System.out.println("Player " + p.getId() + " rolled " + roll + " (stay at " + from + ")");
                    continue;
                }

                int landing = board.applyTransitions(proposed);
                p.setPosition(landing);

                String transitioned = (landing != proposed)
                        ? (" (after snake/ladder: " + proposed + " -> " + landing + ")")
                        : "";
                System.out.println(
                        "Player " + p.getId() + " rolled " + roll +
                                " : " + from + " -> " + proposed + transitioned
                );

                if (landing == board.getMaxCell()) {
                    p.markWon();
                    winners++;
                    System.out.println("Player " + p.getId() + " reached the last cell. Winners: " + winners);
                    if (winners >= targetWinnerCount) {
                        break;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Game finished. Winners:");
        for (Player p : players) {
            if (p.hasWon()) {
                System.out.println("- Player " + p.getId());
            }
        }
    }
}

