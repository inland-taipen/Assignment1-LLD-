import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Elevator {
    private final String elevatorId;
    private int currentFloor;
    private Direction dir;
    private String state;
    private final Set<Integer> scheduledFloors;

    public Elevator(String elevatorId, int initialFloor) {
        if (elevatorId == null || elevatorId.isBlank()) {
            throw new IllegalArgumentException("elevatorId is required");
        }
        if (initialFloor <= 0) {
            throw new IllegalArgumentException("initialFloor must be > 0");
        }
        this.elevatorId = elevatorId;
        this.currentFloor = initialFloor;
        this.dir = Direction.IDLE;
        this.state = "IDLE";
        this.scheduledFloors = new HashSet<>();
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDir() {
        return dir;
    }

    public String getState() {
        return state;
    }

    public boolean isIdle() {
        return scheduledFloors.isEmpty();
    }

    public Set<Integer> getScheduledFloors() {
        return Collections.unmodifiableSet(scheduledFloors);
    }

    public void schedule(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        int target = request.getFloorNumber();

        if (request.getType() == RequestType.EMERGENCY) {
            scheduledFloors.clear();
            scheduledFloors.add(target);
            state = "EMERGENCY";
        } else {
            state = "MOVING";
            scheduledFloors.add(target);
        }

        // If request provides a direction, prefer it; otherwise infer.
        Direction requestedDir = request.getDir();
        if (requestedDir != null && requestedDir != Direction.IDLE) {
            dir = requestedDir;
        } else {
            dir = inferDir(target);
        }
    }

    public void moveOneStep() {
        if (scheduledFloors.isEmpty()) {
            dir = Direction.IDLE;
            state = "IDLE";
            return;
        }

        // If idle but we have work, pick a reasonable direction.
        if (dir == Direction.IDLE) {
            int closest = scheduledFloors.stream()
                    .min((a, b) -> Integer.compare(Math.abs(a - currentFloor), Math.abs(b - currentFloor)))
                    .orElse(currentFloor);
            dir = inferDir(closest);
        }

        int nextStop = pickNextStop();

        if (nextStop == currentFloor) {
            scheduledFloors.remove(currentFloor);
            if (scheduledFloors.isEmpty()) {
                dir = Direction.IDLE;
                state = "IDLE";
                return;
            }
            // Recompute direction and continue next tick.
            dir = inferDir(pickNextStop());
            state = state.equals("EMERGENCY") ? "EMERGENCY" : "MOVING";
            return;
        }

        if (nextStop > currentFloor) {
            currentFloor++;
            dir = Direction.UP;
        } else {
            currentFloor--;
            dir = Direction.DOWN;
        }

        // If we arrived at a scheduled stop, remove it.
        scheduledFloors.remove(currentFloor);

        if (scheduledFloors.isEmpty()) {
            dir = Direction.IDLE;
            state = "IDLE";
            return;
        }

        state = state.equals("EMERGENCY") ? "EMERGENCY" : "MOVING";
    }

    private Direction inferDir(int targetFloor) {
        if (targetFloor > currentFloor) return Direction.UP;
        if (targetFloor < currentFloor) return Direction.DOWN;
        return Direction.IDLE;
    }

    private int pickNextStop() {
        if (scheduledFloors.isEmpty()) return currentFloor;

        if (dir == Direction.UP) {
            return scheduledFloors.stream()
                    .filter(f -> f >= currentFloor)
                    .min(Integer::compareTo)
                    .orElseGet(() -> {
                        // No stops above: switch to DOWN path.
                        dir = Direction.DOWN;
                        return scheduledFloors.stream()
                                .max(Integer::compareTo)
                                .orElse(currentFloor);
                    });
        }

        if (dir == Direction.DOWN) {
            return scheduledFloors.stream()
                    .filter(f -> f <= currentFloor)
                    .max(Integer::compareTo)
                    .orElseGet(() -> {
                        // No stops below: switch to UP path.
                        dir = Direction.UP;
                        return scheduledFloors.stream()
                                .min(Integer::compareTo)
                                .orElse(currentFloor);
                    });
        }

        // Fallback: choose nearest.
        return scheduledFloors.stream()
                .min((a, b) -> Integer.compare(Math.abs(a - currentFloor), Math.abs(b - currentFloor)))
                .orElse(currentFloor);
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id='" + elevatorId + '\'' +
                ", currentFloor=" + currentFloor +
                ", dir=" + dir +
                ", state='" + state + '\'' +
                ", scheduledFloors=" + scheduledFloors +
                '}';
    }
}

