import java.util.Comparator;
import java.util.List;

public class NearestPickElevator implements MovingStrategy {
    @Override
    public Elevator chooseElevator(List<Elevator> elevators, Request request) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalArgumentException("elevators must be non-empty");
        }
        if (request == null) {
            throw new IllegalArgumentException("request is required");
        }

        int target = request.getFloorNumber();

        // Prefer elevators that are idle, then closest in the requested direction.
        return elevators.stream()
                .filter(e -> e != null)
                .min(Comparator
                        .comparingInt((Elevator e) -> e.isIdle() ? 0 : 1)
                        .thenComparingInt(e -> directionDistance(e, target, request))
                        .thenComparingInt(Elevator::getCurrentFloor)
                )
                .orElseThrow(() -> new IllegalStateException("No elevator selected"));
    }

    private int directionDistance(Elevator elevator, int target, Request request) {
        int current = elevator.getCurrentFloor();

        // If emergency, be a bit more decisive: just pick nearest floor regardless of direction.
        if (request.getType() == RequestType.EMERGENCY) {
            return Math.abs(target - current);
        }

        Direction dir = request.getDir();
        if (dir == Direction.UP) {
            if (target >= current) return target - current;
            // If target is behind, penalize heavily.
            return 1_000_000 + (current - target);
        }
        if (dir == Direction.DOWN) {
            if (target <= current) return current - target;
            return 1_000_000 + (target - current);
        }

        return Math.abs(target - current);
    }
}

