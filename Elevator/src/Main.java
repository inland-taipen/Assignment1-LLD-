import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Elevator e1 = new Elevator("E1", 1);
        Elevator e2 = new Elevator("E2", 8);

        List<Floor> floors = Arrays.asList(
                new Floor(1), new Floor(2), new Floor(3), new Floor(4),
                new Floor(5), new Floor(6), new Floor(7), new Floor(8),
                new Floor(9), new Floor(10)
        );

        MovingStrategy strategy = new NearestPickElevator();
        ElevatorSystem system = new ElevatorSystem(Arrays.asList(e1, e2), floors, strategy);

        // Normal requests
        system.addRequest(new Request("R1", 6, Direction.UP, RequestType.NORMAL));
        system.addRequest(new Request("R2", 3, Direction.DOWN, RequestType.NORMAL));

        // Emergency request (will preempt within the chosen elevator)
        system.addRequest(new Request("R3", 10, Direction.UP, RequestType.EMERGENCY));

        // Simulate some ticks.
        for (int tick = 1; tick <= 15; tick++) {
            System.out.println("---- TICK " + tick + " ----");
            system.moveAllOneStep();
            for (Elevator elevator : system.getElevators()) {
                System.out.println(elevator);
            }
        }
    }
}

