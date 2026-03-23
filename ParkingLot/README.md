## Parking Lot Design (UML + Implementation)

This folder contains a Parking Lot (LLD-style) application based on the class diagram structure shown in your sketch.

### UML Class Diagram (PlantUML)

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam packageStyle rectangle

enum VehicleType {
  SMALL
  MEDIUM
  LARGE
}

interface EntryGate {
  +enter(vehicle: Vehicle): Ticket
}

interface ExitGate {
  +exit(ticket: Ticket): PaymentReceipt
}

interface Floor {
  +getSlotFor(type: VehicleType): Slot
  +free(slot: Slot): void
}

class ParkingLot {
  - floors: List<Floor>
  - ratesPerMinute: Map<VehicleType, double>
  - ticketService: TicketService
  - entryGate: EntryGate
  - exitGate: ExitGate
  +park(vehicle: Vehicle): Ticket
  +unpark(ticket: Ticket): PaymentReceipt
  -allocateSlot(vehicle: Vehicle): Slot
}

class Vehicle {
  - vehicleId: String
  - type: VehicleType
}

class Slot {
  - slotId: String
  - type: VehicleType
  - occupied: boolean
}

class Ticket {
  - token: String
  - vehicleType: VehicleType
  - slot: Slot
  - entryTimeMillis: long
}

class PaymentReceipt {
  - token: String
  - minutes: long
  - amount: double
}

class TicketService {
  +createTicket(slot: Slot, vehicle: Vehicle): Ticket
}

class RateCalculator {
  +calculate(slotType: VehicleType, entryTimeMillis: long, exitTimeMillis: long): double
}

class StandardFloor {
}

EntryGate --> Ticket
ExitGate --> PaymentReceipt
Floor --> Slot
ParkingLot --> EntryGate
ParkingLot --> ExitGate
ParkingLot --> TicketService
ParkingLot --> RateCalculator

ParkingLot *-- Vehicle
ParkingLot *-- Slot
ParkingLot *-- Ticket

StandardFloor --|> Floor

@enduml
```

### Mermaid Class Diagram (renders on GitHub)

```mermaid
classDiagram
direction TB

class ParkingLot{
  - floors: List~Floor~
  - ratesPerMinute: Map~VehicleType, double~
  - ticketService: TicketService
  + park(vehicle: Vehicle): Ticket
  + unpark(ticket: Ticket): PaymentReceipt
}

class Vehicle{
  - vehicleId: String
  - type: VehicleType
}

class Slot{
  - slotId: String
  - type: VehicleType
  - occupied: boolean
}

class Ticket{
  - token: String
  - vehicleType: VehicleType
  - slot: Slot
  - entryTimeMillis: long
}

class PaymentReceipt{
  - token: String
  - minutes: long
  - amount: double
}

class TicketService{
  + createTicket(slot: Slot, vehicle: Vehicle): Ticket
}

class RateCalculator{
  + calculate(slotType: VehicleType, entry: long, exit: long): double
}

class EntryGate{
  <<interface>>
  + enter(vehicle: Vehicle): Ticket
}

class ExitGate{
  <<interface>>
  + exit(ticket: Ticket): PaymentReceipt
}

class Floor{
  <<interface>>
  + getSlotFor(type: VehicleType): Slot
  + free(slot: Slot): void
}

class StandardFloor{
}

Floor <|-- StandardFloor
EntryGate <|.. ParkingLot
ExitGate <|.. ParkingLot
ParkingLot --> TicketService
ParkingLot --> RateCalculator
ParkingLot --> Floor
ParkingLot --> EntryGate
ParkingLot --> ExitGate
ParkingLot --> Slot
Ticket --> Slot
TicketService --> Ticket
```

### How to Run

```bash
cd ParkingLot

# compile (requires Java)
javac -d out $(find src -name "*.java")

# run
java -cp out com.example.parkinglot.Main
```

### Console Inputs (quick)
- Enter `floors`
- Enter `smallSlotsPerFloor`, `mediumSlotsPerFloor`, `largeSlotsPerFloor`
- Enter `ratePerMinuteSmall`, `ratePerMinuteMedium`, `ratePerMinuteLarge`

Then repeatedly:
- `park <vehicleId> <SMALL|MEDIUM|LARGE>`
- `exit <ticketToken>`
- `quit`

