## Elevator LLD (UML)

### UML (PlantUML)

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam packageStyle rectangle

enum Direction {
  UP
  DOWN
  IDLE
}

enum RequestType {
  NORMAL
  EMERGENCY
}

interface MovingStrategy {
  +chooseElevator(elevators: List<Elevator>, request: Request): Elevator
}

class NearestPickElevator {
  +chooseElevator(elevators: List<Elevator>, request: Request): Elevator
}

class Request {
  - requestId: String
  - floorNumber: int
  - dir: Direction
  - type: RequestType
}

class ElevatorSystem {
  - elevators: List<Elevator>
  - floors: List<Floor>
  - strategy: MovingStrategy

  +addRequest(request: Request): void
  +dispatch(request: Request): void
}

class Elevator {
  - currentFloor: int
  - dir: Direction
  - state: String
  - scheduledFloors: Set<int>

  +moveOneStep(): void
  +schedule(request: Request): void
  +isIdle(): boolean
}

class Floor {
  - floorId: int
}

MovingStrategy <|.. NearestPickElevator
ElevatorSystem --> MovingStrategy
ElevatorSystem --> Elevator
ElevatorSystem --> Floor

Elevator --> Direction
Request --> Direction
Request --> RequestType

Floor "1" o-- "*" Elevator : serves
Elevator *-- "0..*" Request : scheduled
@enduml
```

### UML (Mermaid - renders on GitHub)

```mermaid
classDiagram
direction TB

class Direction{<<enumeration>> UP DOWN IDLE}
class RequestType{<<enumeration>> NORMAL EMERGENCY}

class Request{
  -requestId: String
  -floorNumber: int
  -dir: Direction
  -type: RequestType
}

class MovingStrategy{
  <<interface>>
  +chooseElevator(elevators: List~Elevator~, request: Request): Elevator
}

class NearestPickElevator{
  +chooseElevator(elevators: List~Elevator~, request: Request): Elevator
}

class ElevatorSystem{
  -elevators: List~Elevator~
  -floors: List~Floor~
  -strategy: MovingStrategy
  +addRequest(request: Request): void
  +dispatch(request: Request): void
}

class Elevator{
  -currentFloor: int
  -dir: Direction
  -state: String
  -scheduledFloors: Set~int~
  +moveOneStep(): void
  +schedule(request: Request): void
  +isIdle(): boolean
}

class Floor{
  -floorId: int
}

MovingStrategy <|.. NearestPickElevator
ElevatorSystem --> MovingStrategy
ElevatorSystem --> Elevator
ElevatorSystem --> Floor

Request --> Direction
Elevator --> Direction
Request --> RequestType
```

### Java Code

Java implementation is in `Elevator/src/`:
- `Direction`, `RequestType`, `Request`
- `Floor`
- `MovingStrategy`, `NearestPickElevator`
- `Elevator`, `ElevatorSystem`
- `Main` (demo)

How to run (requires Java):

```bash
cd Elevator
mkdir -p out
javac -d out src/*.java
java -cp out Main
```

