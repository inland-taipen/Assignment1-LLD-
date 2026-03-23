package com.example.parkinglot;

import java.util.UUID;

public class TicketService {
    public Ticket createTicket(Slot slot, Vehicle vehicle, long entryTimeMillis) {
        if (slot == null) {
            throw new IllegalArgumentException("slot cannot be null");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle cannot be null");
        }
        if (vehicle.getType() != slot.getType()) {
            throw new IllegalArgumentException("vehicle type does not match slot type");
        }

        String token = UUID.randomUUID().toString();
        return new Ticket(token, vehicle.getType(), slot, entryTimeMillis);
    }
}

