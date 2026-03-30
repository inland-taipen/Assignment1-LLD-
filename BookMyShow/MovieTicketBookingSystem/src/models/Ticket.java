package models;

import java.util.List;

public class Ticket {
    private String ticketId;
    private User user;
    private Theatre theatre;
    private Movie movie;
    private Show show;
    private List<Seat> seats;
    private double totalAmount;
    private TicketStatus status;

    public Ticket(String ticketId, User user, Show show, List<Seat> seats, double totalAmount, TicketStatus status) {
        this.ticketId = ticketId;
        this.user = user;
        this.theatre = show.getTheatre();
        this.movie = show.getMovie();
        this.show = show;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    public String getTicketId() { return ticketId; }
    public User getUser() { return user; }
    public Theatre getTheatre() { return theatre; }
    public Movie getMovie() { return movie; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public TicketStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Ticket[ID: " + ticketId + ", Status: " + status + ", Movie: " + movie.getName() + ", Amount: Rs" + totalAmount + "]";
    }
}
