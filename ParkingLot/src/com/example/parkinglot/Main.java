package com.example.parkinglot;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of floors: ");
        int floors = sc.nextInt();

        System.out.print("Enter small slots per floor: ");
        int smallSlots = sc.nextInt();

        System.out.print("Enter medium slots per floor: ");
        int mediumSlots = sc.nextInt();

        System.out.print("Enter large slots per floor: ");
        int largeSlots = sc.nextInt();

        System.out.print("Rate per minute for SMALL: ");
        double rSmall = sc.nextDouble();

        System.out.print("Rate per minute for MEDIUM: ");
        double rMed = sc.nextDouble();

        System.out.print("Rate per minute for LARGE: ");
        double rLarge = sc.nextDouble();

        Map<VehicleType, Double> rates = new HashMap<>();
        rates.put(VehicleType.SMALL, rSmall);
        rates.put(VehicleType.MEDIUM, rMed);
        rates.put(VehicleType.LARGE, rLarge);

        ParkingLot parkingLot = new ParkingLot(floors, smallSlots, mediumSlots, largeSlots, rates);

        System.out.println();
        System.out.println("Commands:");
        System.out.println("- park <vehicleId> <SMALL|MEDIUM|LARGE>");
        System.out.println("- exit <ticketToken>");
        System.out.println("- quit");
        System.out.println();

        // Simple token lookup by the session; in a real system we'd persist tickets.
        Map<String, Ticket> activeTickets = new HashMap<>();

        while (true) {
            System.out.print("> ");
            String cmd = sc.next();

            if (cmd.equalsIgnoreCase("quit")) {
                break;
            }

            if (cmd.equalsIgnoreCase("park")) {
                String vehicleId = sc.next();
                String typeRaw = sc.next();
                VehicleType type = VehicleType.fromString(typeRaw);

                Vehicle v = new Vehicle(vehicleId, type);
                Ticket ticket = parkingLot.getEntryGate().enter(v);
                activeTickets.put(ticket.getToken(), ticket);

                System.out.println("Parked. Ticket token: " + ticket.getToken());
                continue;
            }

            if (cmd.equalsIgnoreCase("exit")) {
                String token = sc.next();
                Ticket ticket = activeTickets.get(token);
                if (ticket == null) {
                    System.out.println("Unknown ticket token. (Maybe already exited?)");
                    continue;
                }

                PaymentReceipt receipt = parkingLot.getExitGate().exit(ticket);
                activeTickets.remove(token);

                System.out.println("Exited. Minutes: " + receipt.getMinutes() + ", Amount: " + receipt.getAmount());
                continue;
            }

            System.out.println("Unknown command: " + cmd);
        }

        System.out.println("Bye.");
    }
}

