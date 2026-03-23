package com.example.snakesandladders;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter n (board size, n x n): ");
        int n = scanner.nextInt();

        System.out.print("Enter x (number of players): ");
        int x = scanner.nextInt();

        System.out.print("Enter difficulty_level (easy/hard): ");
        String difficultyRaw = scanner.next();

        DifficultyLevel difficulty = DifficultyLevel.fromString(difficultyRaw);

        Board board = BoardGenerator.generate(n, difficulty, new Random());
        Dice dice = new Dice(1, 6, new Random());

        // Requirement: "continue till there are at least 2 players ... to win"
        // Interpreted here as: stop once at least 2 players have reached the last cell.
        int targetWinners = 2;

        Game game = new Game(board, dice, x, targetWinners);
        game.play();
    }
}

