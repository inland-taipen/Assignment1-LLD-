package models;

import java.util.List;

public class User {
    private String id;
    private String name;
    private Role role;

    // Overloaded Main Constructor mapped with Role
    public User(String id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    // Default configuration ensures backwards compatibility creating standard Customers
    public User(String id, String name) {
        this(id, name, Role.CUSTOMER);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Role getRole() { return role; }

    public Ticket bookTicket(services.BookingService service, String showId, List<String> seatIds, services.PaymentService paymentService) {
        return service.bookTicket(this, showId, seatIds, paymentService);
    }
}
