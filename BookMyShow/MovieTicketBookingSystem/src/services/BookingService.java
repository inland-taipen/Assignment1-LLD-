package services;

import models.*;
import java.util.*;

public class BookingService {
    
    // Decoupling dependency specifically maintaining the exact necessary query linkage
    private CatalogService catalogService;

    public BookingService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Pure transactional API handling specific validation protocols cleanly
    public Ticket bookTicket(User user, String showId, List<String> seatIds, PaymentService paymentService) {
        Show show = catalogService.getShow(showId);
        if (show == null) {
            throw new IllegalArgumentException("Invalid Show ID");
        }

        List<Seat> bookedSeats = new ArrayList<>();
        double totalAmount = 0.0;
        double basePay = 100.0; 

        synchronized (show) {
            Map<String, Seat> availableSeats = show.getShowSeats();

            for (String seatId : seatIds) {
                Seat seat = availableSeats.get(seatId);
                if (seat == null || !seat.isAvail()) {
                    throw new IllegalStateException("Seat " + seatId + " is unavailable or already claimed.");
                }
            }

            for (String seatId : seatIds) {
                Seat seat = availableSeats.get(seatId);
                seat.setAvail(false);
                bookedSeats.add(seat);
                totalAmount += new Payment(basePay, seat.getType()).calc();
            }
        }

        boolean paymentSuccess = paymentService.processPayment(totalAmount, user.getId());

        if (!paymentSuccess) {
            synchronized (show) {
                for (Seat seat : bookedSeats) seat.setAvail(true); 
            }
            throw new RuntimeException("Payment Failed! Releasing seats to the public.");
        }

        String ticketId = UUID.randomUUID().toString();
        return new Ticket(ticketId, user, show, bookedSeats, totalAmount, TicketStatus.CONFIRMED);
    }
}
