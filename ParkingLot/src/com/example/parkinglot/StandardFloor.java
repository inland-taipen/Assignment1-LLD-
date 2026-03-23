package com.example.parkinglot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * Simple floor implementation:
 * - maintains a queue of available slots per vehicle type
 * - allocates the first available slot for the requested type
 */
public class StandardFloor implements Floor {
    private final int floorNumber;
    private final Map<VehicleType, Deque<Slot>> availableSlotsByType;
    private final List<Slot> allSlots;

    public StandardFloor(int floorNumber, Map<VehicleType, Deque<Slot>> availableSlotsByType, List<Slot> allSlots) {
        if (floorNumber < 1) {
            throw new IllegalArgumentException("floorNumber must be >= 1");
        }
        if (availableSlotsByType == null) {
            throw new IllegalArgumentException("availableSlotsByType cannot be null");
        }
        if (allSlots == null) {
            throw new IllegalArgumentException("allSlots cannot be null");
        }
        this.floorNumber = floorNumber;
        this.availableSlotsByType = availableSlotsByType;
        this.allSlots = allSlots;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public Slot getSlotFor(VehicleType type) {
        Deque<Slot> queue = availableSlotsByType.get(type);
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        return queue.pollFirst();
    }

    @Override
    public void free(Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("slot cannot be null");
        }
        if (slot.getType() == null) {
            throw new IllegalArgumentException("slot type cannot be null");
        }
        Deque<Slot> queue = availableSlotsByType.get(slot.getType());
        if (queue == null) {
            queue = new ArrayDeque<>();
            availableSlotsByType.put(slot.getType(), queue);
        }
        queue.addLast(slot);
    }

    public List<Slot> getAllSlots() {
        return allSlots;
    }
}

