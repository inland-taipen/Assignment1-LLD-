package com.example.pen;

public final class Main {
    public static void main(String[] args) {
        InkTank tank = new InkTank(10, 6);
        Cap cap = new Cap(true);
        Pen pen = new Pen(tank, cap);

        Ink ballInk = new Ink("ball", "Blue");
        Ink gelInk = new Ink("gel", "Red");
        Bottle bottleBall = new Bottle(ballInk, 10);
        Bottle bottleGel = new Bottle(gelInk, 10);

        System.out.println("Initial ink ml: " + pen.getInkMl());
        pen.refill(bottleBall);
        System.out.println("After refill -> " + pen.getInkType() + ", " + pen.getInkColor());

        try {
            pen.start(); // start() opens cap
            pen.close(); // close cap again
            pen.write("HELLO");
        } catch (Exception e) {
            System.out.println("Write exception (cap case): " + e.getMessage());
        }

        try {
            pen.start();
            pen.write("12345678");
            pen.close();

            pen.start();
            String firstWrite = pen.write("ABCDEFG");
            pen.close();
            System.out.println("First write: " + firstWrite);
        } catch (Exception e) {
            System.out.println("Write exception (out-of-ink case): " + e.getMessage());
        }

        pen.refill(bottleGel);
        System.out.println("After gel refill -> " + pen.getInkType() + ", " + pen.getInkColor());

        pen.start();
        String ok = pen.write("XYZ");
        pen.close();
        System.out.println("Write success: " + ok + " | final ink ml: " + pen.getInkMl());

        try {
            Pen penNoCap = new Pen(new InkTank(5, 5));
            penNoCap.write("HI");
        } catch (Exception e) {
            System.out.println("Write exception (click pen case): " + e.getMessage());
        }

        Pen clickPen = new Pen(new InkTank(5, 5));
        clickPen.start();
        String ok2 = clickPen.write("HI");
        clickPen.close();
        System.out.println("Click pen write success: " + ok2);
    }
}

