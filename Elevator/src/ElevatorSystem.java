import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ElevatorSystem {
    private final List<Elevator> elevators;
    private final List<Floor> floors;
    private final MovingStrategy strategy;

    public ElevatorSystem(List<Elevator> elevators, List<Floor> floors, MovingStrategy strategy) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalArgumentException("elevators must be non-empty");
        }
        this.elevators = new ArrayList<>(elevators);
        this.floors = floors == null ? new ArrayList<>() : new ArrayList<>(floors);
        this.strategy = Objects.requireNonNull(strategy, "strategy is required");
    }

    public void addRequest(Request request) {
        dispatch(request);
    }

    public void dispatch(Request request) {
        Elevator elevator = strategy.chooseElevator(elevators, request);
        elevator.schedule(request);
    }

    public void moveAllOneStep() {
        for (Elevator elevator : elevators) {
            elevator.moveOneStep();
        }
    }

    public List<Elevator> getElevators() {
        return new ArrayList<>(elevators);
    }
}

