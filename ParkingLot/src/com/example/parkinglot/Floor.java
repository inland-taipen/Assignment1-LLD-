package com.example.parkinglot;

public interface Floor {
    Slot getSlotFor(VehicleType type);

    void free(Slot slot);
}

