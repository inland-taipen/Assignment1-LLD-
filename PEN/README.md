## Pen Design (Composition + Aggregation UML)

This folder contains a simple **Pen** implementation with these functionalities:
- `start()` (opens the cap / activates the pen)
- `write(text)` (consumes ink and returns what was written)
- `close()` (closes the cap / deactivates the pen)
- `refill(bottle)` (refills ink from an external bottle)

### UML Class Diagram (PlantUML)

```plantuml
@startuml
skinparam classAttributeIconSize 0

class Pen {
  - tank: InkTank
  - cap: Cap
  - currentInk: Ink
  - started: boolean
  + start(): void
  + write(text: String): String
  + close(): void
  + refill(bottle: Bottle): int
  + getInkColor(): String
  + getInkType(): String
}

class InkTank {
  - capacityMl: int
  - inkMl: int
  + consumeInk(units: int): int
  + addInk(units: int): void
  + getInkMl(): int
  + getRemainingCapacityMl(): int
}

class Cap {
  - closed: boolean
  + open(): void
  + close(): void
  + isClosed(): boolean
}

class Bottle {
  - remainingMl: int
  - ink: Ink
  + pourInto(tank: InkTank): int
  + getRemainingMl(): int
  + getInk(): Ink
}

' Ink model (type + color).
class Ink {
  - type: String
  - color: String
  + getType(): String
  + getColor(): String
}

' Composition: Pen owns the tank (lifetime idea).
Pen *-- InkTank : contains

' Aggregation: Pen "has" the cap (separate object idea).
' (Pen can also be created without cap to show different error messages.)
Pen o-- Cap : has (optional)

' Aggregation: Pen holds current ink (set on every refill).
Pen o-- Ink : currentInk

' Bottle carries the ink (composition idea).
Bottle *-- Ink : carries

' Bottle is given to refill; Pen does not own the bottle.
Pen o..> Bottle : refill uses

@enduml
```

### Relationship meaning
- **Composition (`*--`)**: the `Pen` contains an `InkTank`. When the `Pen` goes away, the tank is considered part of the pen.
- **Aggregation (`o--`)**: the `Pen` has a `Cap`, but the cap is not owned with the same strict lifetime as the tank.
- **Refill dependency (`o..>`)**: a `Bottle` is provided from outside; the pen does not own the bottle.

### Relationship Picture (Mermaid)

```mermaid
classDiagram
  class Pen {
    +start()
    +write(text)
    +close()
    +refill(bottle)
    +getInkColor()
    +getInkType()
  }
  class InkTank {
    +consumeInk(units)
    +addInk(units)
  }
  class Cap {
    +open()
    +close()
    +isClosed()
  }
  class Ink {
    +getType()
    +getColor()
  }
  class Bottle {
    +pourInto(tank)
    +getRemainingMl()
    +getInk()
  }

  Pen *-- InkTank : contains
  Pen o-- Cap : has
  Pen o-- Ink : current ink (changes on refill)
  Bottle *-- Ink : carries
  Pen ..> Bottle : refill uses
```

### What I added (where) - quick notes
- In `Pen.start()`: opens the `Cap` and sets `started=true`.
- In `Pen.write()`: consumes ink from `InkTank` (1 ink unit per written character).
- In `Pen.close()`: closes the `Cap` and sets `started=false`.
- In `Pen.write()`: throws different exception messages:
  - if it is a click pen (no cap) and you didn't start it, it throws "Click pen is not clicked yet..."
  - if cap is closed, it throws "Cap is closed..."
  - if ink is not enough for the full text, it throws "Out of ink. Refill first."
- In `Pen.refill(bottle)`: uses the external `Bottle` to pour ink into `InkTank` AND updates the pen's `currentInk` (so color/type changes every refill).

### Demo
Run:
- `PEN/src/com/example/pen/Main.java`

