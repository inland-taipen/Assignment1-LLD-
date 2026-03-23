package com.example.parkinglot;

public class EntryGateImpl implements EntryGate {
    private final ParkingLot parkingLot;

    public EntryGateImpl(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("parkingLot cannot be null");
        }
        this.parkingLot = parkingLot;
    }

    @Override
    public Ticket enter(Vehicle vehicle) {
        return parkingLot.park(vehicle);
    }
}

