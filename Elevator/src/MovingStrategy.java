import java.util.List;

public interface MovingStrategy {
    Elevator chooseElevator(List<Elevator> elevators, Request request);
}

