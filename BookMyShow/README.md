## BookMyShow (LLD)

### UML (PlantUML)

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam packageStyle rectangle

enum Role {
  ADMIN
  CUSTOMER
}

class User {
  - userId: String
  - name: String
  - role: Role
}

class CatalogService {
  - cities: Map<String, City>
  - shows: Map<String, Show>
  + addCity(admin: User, city: City): void
  + addMovie(admin: User, cityName: String, movie: Movie): void
  + addTheatre(admin: User, cityName: String, theatre: Theatre): void
  + addShow(admin: User, show: Show): void
  + getShow(showName: String): Show
}

class BookingService {
  - catalogService: CatalogService
  - paymentGateway: PaymentGateway
  - ticketService: TicketService
  + bookTickets(customer: User, showName: String, seatIds: List<String>, paymentMethod: String): BookingReceipt
}

class PaymentGateway {
  + initiateTransaction(amount: double, user: User, paymentMethod: String): PaymentResult
}

class PaymentResult {
  - success: boolean
  - transactionId: String
}

class BookingReceipt {
  - receiptId: String
  - bookingId: String
  - totalAmount: double
  - transactionId: String
}

class TicketService {
  + createTickets(customer: User, show: Show, seats: List<Seat>): List<Ticket>
}

class Booking {
  - bookingId: String
  - customer: User
  - show: Show
  - tickets: List<Ticket>
  - status: BookingStatus
}

enum BookingStatus {
  PENDING
  CONFIRMED
  FAILED
}

class Ticket {
  - ticketId: String
  - customerId: String
  - showName: String
  - seatId: String
  - price: double
}

class City {
  - name: String
}

class Movie {
  - movieId: String
  - title: String
}

class Theatre {
  - theatreId: String
  - name: String
}

class Show {
  - showName: String
  - movie: Movie
  - theatre: Theatre
  - startTime: LocalDateTime
  - seatsById: Map<String, Seat>
}

class Seat {
  - seatId: String
  - price: double
  - booked: boolean
}

User --> Role
CatalogService --> City
CatalogService --> Show
CatalogService --> Movie
CatalogService --> Theatre
Show --> Movie
Show --> Theatre
Show *-- Seat
BookingService --> CatalogService
BookingService --> PaymentGateway
BookingService --> TicketService
TicketService --> Ticket
BookingService --> BookingReceipt
BookingReceipt --> Booking
Booking *-- Ticket
Ticket --> Seat

@enduml
```

### UML (Mermaid - renders on GitHub)

```mermaid
classDiagram
direction TB

class Role{<<enumeration>> ADMIN CUSTOMER}
class User{ -userId: String -name: String -role: Role }

class CatalogService{
  -cities: Map~String, City~
  -shows: Map~String, Show~
  +addCity(admin: User, city: City): void
  +addMovie(admin: User, cityName: String, movie: Movie): void
  +addTheatre(admin: User, cityName: String, theatre: Theatre): void
  +addShow(admin: User, show: Show): void
  +getShow(showName: String): Show
}

class BookingService{
  -catalogService: CatalogService
  -paymentGateway: PaymentGateway
  -ticketService: TicketService
  +bookTickets(customer: User, showName: String, seatIds: List~String~, paymentMethod: String): BookingReceipt
}

class PaymentGateway{
  +initiateTransaction(amount: double, user: User, paymentMethod: String): PaymentResult
}
class PaymentResult{ -success: boolean -transactionId: String }
class BookingReceipt{ -receiptId: String -bookingId: String -totalAmount: double -transactionId: String }
class TicketService{ +createTickets(customer: User, show: Show, seats: List~Seat~): List~Ticket~ }

class Booking{ -bookingId: String -customer: User -show: Show -tickets: List~Ticket~ -status: BookingStatus }
class BookingStatus{<<enumeration>> PENDING CONFIRMED FAILED}

class Ticket{ -ticketId: String -customerId: String -showName: String -seatId: String -price: double }
class City{ -name: String }
class Movie{ -movieId: String -title: String }
class Theatre{ -theatreId: String -name: String }
class Show{ -showName: String -movie: Movie -theatre: Theatre -startTime: LocalDateTime -seatsById: Map~String, Seat~ }
class Seat{ -seatId: String -price: double -booked: boolean }

User --> Role : role
CatalogService --> City
CatalogService --> Movie
CatalogService --> Theatre
CatalogService --> Show
Show --> Movie
Show --> Theatre
Show *-- Seat

BookingService --> CatalogService
BookingService --> PaymentGateway
BookingService --> TicketService
BookingService --> BookingReceipt
BookingReceipt --> Booking
Booking *-- Ticket
Ticket --> Seat
```

### What’s implemented

This folder contains a runnable Java implementation of a simplified BookMyShow LLD:
- `CatalogService` manages `City`, `Movie`, `Theatre`, `Show`
- `BookingService` reserves `Seat`s, calls `PaymentGateway`, generates `Ticket`s, and returns a `BookingReceipt`

### How to Run

```bash
cd BookMyShow/MovieTicketBookingSystem

# compile (requires Java)
mkdir -p out
javac -d out $(find src -name "*.java")

# run
java -cp out Main
```

