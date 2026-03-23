## Snakes & Ladders Design (Class Diagram) + Implementation

This folder contains a small Snakes & Ladders (LLD-style) application that matches the given requirements:

- Takes user input:
  - `n` (board size = `n x n`)
  - `x` (number of players)
  - `difficulty_level` (`easy` / `hard`)
- Places `n` snakes and `n` ladders randomly on the `n^2` board cells.
- Ensures:
  - Snake tail is always smaller than snake head.
  - Ladder end is always larger than ladder start.
  - No cycles are created by the snake/ladder transitions (DAG).
- Uses a six-sided dice (`1..6`) for each player turn.
- Starts each player piece at position `0` (outside the board).
- Moves piece turn-by-turn and applies snake/ladder transitions immediately after landing.
- A player wins when they reach the last cell (`n^2`).
- Game ends after at least **2** players win (configurable in `Game`).
- If a dice move would go beyond the board end (and also the assignment’s `100` cap), the piece does not move.

### UML Class Diagram (PlantUML) - matches your picture

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam packageStyle rectangle

package "Core Classes" {
  class Game {
    - board: Board
    - players: List<Player>
    - dice: Dice
    - status: GameStatus
    + play(): void
  }

  class Dice {
    - minValue: int
    - maxValue: int
    + roll(): int
  }

  class Board {
    - size: int
    - snakesAndLadders: Map<Integer, BoardEntity>
    + applyTransitions(position: int): int
  }
}

package "Enums" {
  enum GameStatus {
    NOT_STARTED
    RUNNING
    FINISHED
  }
}

package "Data Classes" {
  class Player {
    - name: String
    - position: int
  }

  abstract class BoardEntity {
    - start: int
    - end: int
  }
}

package "Inheritance Hierarchy" {
  class Snake {
  }

  class Ladder {
  }
}

Game *-- Board
Game *-- Player
Dice --> Game : manages
Game --> GameStatus : status

Board o-- "0..*" BoardEntity : stores

Snake --|> BoardEntity : start > end
Ladder --|> BoardEntity : start < end

@enduml
```

### Mermaid Class Diagram (renders on GitHub)

```mermaid
classDiagram
direction TB

class Game {
  - board: Board
  - players: List<Player>
  - dice: Dice
  - status: GameStatus
  + play(): void
}

class Dice {
  - minValue: int
  - maxValue: int
  + roll(): int
}

class Board {
  - size: int
  - snakesAndLadders: Map
  + applyTransitions(position: int): int
}

class Player {
  - name: String
  - position: int
}

class GameStatus {
  NOT_STARTED
  RUNNING
  FINISHED
}

class BoardEntity {
  - start: int
  - end: int
}

class Snake
class Ladder

Game *-- Board
Game *-- Player
Dice --> Game : manages
Game --> GameStatus : status

Board o-- "0..*" BoardEntity : stores

BoardEntity <|-- Snake : start > end
BoardEntity <|-- Ladder : start < end
```

### Relationship Meaning (Quick Notes)
- `Game` controls turn order, win condition, and `GameStatus`.
- `Dice` provides the 1..6 random roll for each turn.
- `Board` owns `snakesAndLadders` and applies the transition to the landing cell.
- `BoardEntity` is the base type for both `Snake` and `Ladder` (with different start/end constraints).
- `BoardGenerator` randomly generates snakes and ladders while preventing cycles (DAG).

### Demo / Run

```bash
cd SnakesAndLadders

# compile
javac -d out $(find src -name "*.java")

# run
java -cp out com.example.snakesandladders.Main
```

### Example Input
- `n`: `10`
- `x`: `3`
- `difficulty_level`: `easy`

