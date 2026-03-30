import models.*;
import services.BookingService;
import services.CatalogService;
import services.CreditCardPaymentService;
import services.PaymentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Enforcing Decoupled Systems (SRP)
        CatalogService catalogService = new CatalogService();
        BookingService bookingService = new BookingService(catalogService);
        PaymentService myCreditCardProcessor = new CreditCardPaymentService();
        
        // Setup Users with RBAC Roles
        User admin = new User("A1", "Root_Admin", Role.ADMIN);
        User alice = new User("U1", "Alice", Role.CUSTOMER);
        User bob = new User("U2", "Bob", Role.CUSTOMER);

        System.out.println("--- Admin System Logic Flow Execution ---");
        // 1. Admin populates Catalog
        City city = new City("Metropolis");
        catalogService.addCity(admin, city);

        Movie movie = new Movie("Inception", "English");
        catalogService.addMovie(admin, "Metropolis", movie);

        // 2. Admin adds Theatres
        Theatre theatre = catalogService.addTheatre(admin, "Metropolis", "T1", "Grand Cinema");
        theatre.addSeat(new Seat("A1", SeatType.REGULAR));
        theatre.addSeat(new Seat("A2", SeatType.PREMIUM));
        theatre.addSeat(new Seat("A3", SeatType.VIP));

        // 3. Admin launches the Show
        Show show = catalogService.addMovieShow(admin, "S1", movie, theatre, LocalDateTime.now().plusHours(2));
        System.out.println("Admin 'Root_Admin' successfully populated the Metropolis Database.");

        // Testing RBAC: Customer trying to perform Admin duties
        System.out.println("\nBob attempting to hack the system by adding his own Movie...");
        try {
            catalogService.addMovie(bob, "Metropolis", new Movie("Hackers", "English"));
        } catch (SecurityException e) {
            System.err.println("Security Validation Stopped Bob: " + e.getMessage());
        }

        System.out.println("\n--- Ticket Transaction Log ---");
        try {
            System.out.println("Customer Alice is paying to book seats [A1, A2]...");
            Ticket ticket = alice.bookTicket(bookingService, "S1", Arrays.asList("A1", "A2"), myCreditCardProcessor);
            System.out.println("\nBooking Result: " + ticket);
            
            System.out.println("\nCustomer Bob attempting to book Alice's physical seat (A1)...");
            Ticket failedTicket = bob.bookTicket(bookingService, "S1", Arrays.asList("A1", "A3"), myCreditCardProcessor);
            
        } catch (Exception e) {
            System.err.println("\nTransaction Validation Stopped Bob: " + e.getMessage());
        }
    }
}
