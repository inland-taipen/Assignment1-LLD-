package com.example.parkinglot;

public class ExitGateImpl implements ExitGate {
    private final ParkingLot parkingLot;

    public ExitGateImpl(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("parkingLot cannot be null");
        }
        this.parkingLot = parkingLot;
    }

    @Override
    public PaymentReceipt exit(Ticket ticket) {
        return parkingLot.unpark(ticket);
    }
}

