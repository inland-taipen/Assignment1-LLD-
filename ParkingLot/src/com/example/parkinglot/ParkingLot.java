package com.example.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Deque;
import java.util.ArrayDeque;

public class ParkingLot {
    private final List<StandardFloor> floors;
    private final Map<Integer, StandardFloor> floorByNumber;

    private final Map<VehicleType, Double> ratesPerMinute;

    private final TicketService ticketService;
    private final RateCalculator rateCalculator;

    private final EntryGate entryGate;
    private final ExitGate exitGate;

    public ParkingLot(
            int floorsCount,
            int smallSlotsPerFloor,
            int mediumSlotsPerFloor,
            int largeSlotsPerFloor,
            Map<VehicleType, Double> ratesPerMinute
    ) {
        if (floorsCount < 1) {
            throw new IllegalArgumentException("floorsCount must be >= 1");
        }
        if (ratesPerMinute == null) {
            throw new IllegalArgumentException("ratesPerMinute cannot be null");
        }

        this.ratesPerMinute = new HashMap<>(ratesPerMinute);
        this.ticketService = new TicketService();
        this.rateCalculator = new RateCalculator(this.ratesPerMinute);

        this.floors = new ArrayList<>();
        this.floorByNumber = new HashMap<>();

        for (int floorNumber = 1; floorNumber <= floorsCount; floorNumber++) {
            Map<VehicleType, Deque<Slot>> availableByType = new HashMap<>();
            availableByType.put(VehicleType.SMALL, new ArrayDeque<>());
            availableByType.put(VehicleType.MEDIUM, new ArrayDeque<>());
            availableByType.put(VehicleType.LARGE, new ArrayDeque<>());

            List<Slot> allSlots = new ArrayList<>();

            for (int i = 1; i <= smallSlotsPerFloor; i++) {
                Slot slot = new Slot("F" + floorNumber + "-S-" + i, floorNumber, VehicleType.SMALL);
                availableByType.get(VehicleType.SMALL).addLast(slot);
                allSlots.add(slot);
            }
            for (int i = 1; i <= mediumSlotsPerFloor; i++) {
                Slot slot = new Slot("F" + floorNumber + "-M-" + i, floorNumber, VehicleType.MEDIUM);
                availableByType.get(VehicleType.MEDIUM).addLast(slot);
                allSlots.add(slot);
            }
            for (int i = 1; i <= largeSlotsPerFloor; i++) {
                Slot slot = new Slot("F" + floorNumber + "-L-" + i, floorNumber, VehicleType.LARGE);
                availableByType.get(VehicleType.LARGE).addLast(slot);
                allSlots.add(slot);
            }

            StandardFloor floor = new StandardFloor(floorNumber, availableByType, allSlots);
            this.floors.add(floor);
            this.floorByNumber.put(floorNumber, floor);
        }

        this.entryGate = new EntryGateImpl(this);
        this.exitGate = new ExitGateImpl(this);
    }

    public Ticket park(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle cannot be null");
        }
        Slot slot = allocateSlot(vehicle);
        if (slot == null) {
            throw new IllegalStateException("No available slot for vehicle type: " + vehicle.getType());
        }

        slot.park(vehicle);
        long now = System.currentTimeMillis();
        return ticketService.createTicket(slot, vehicle, now);
    }

    public PaymentReceipt unpark(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("ticket cannot be null");
        }

        Slot slot = ticket.getSlot();
        if (slot == null) {
            throw new IllegalArgumentException("Invalid ticket: missing slot");
        }

        int floorNumber = slot.getFloorNumber();
        StandardFloor floor = floorByNumber.get(floorNumber);
        if (floor == null) {
            throw new IllegalStateException("No floor found for slot floorNumber=" + floorNumber);
        }

        // Release slot and compute payment
        Vehicle parkedVehicle = slot.unpark();
        floor.free(slot);

        long exitTime = System.currentTimeMillis();
        double amount = rateCalculator.calculate(ticket.getVehicleType(), ticket.getEntryTimeMillis(), exitTime);
        long minutes = Math.max(1, (long) Math.ceil((exitTime - ticket.getEntryTimeMillis()) / 60_000.0));

        return new PaymentReceipt(ticket.getToken(), minutes, amount);
    }

    private Slot allocateSlot(Vehicle vehicle) {
        // Simple rule: first available slot across floors.
        for (StandardFloor floor : floors) {
            Slot slot = floor.getSlotFor(vehicle.getType());
            if (slot != null) {
                return slot;
            }
        }
        return null;
    }

    // Exposed for diagram completeness / potential use.
    public EntryGate getEntryGate() {
        return entryGate;
    }

    public ExitGate getExitGate() {
        return exitGate;
    }

    public List<StandardFloor> getFloors() {
        return floors;
    }
}

