package models;

import java.util.HashMap;
import java.util.Map;

public class Theatre {
    private String id;
    private String name;
    private Map<String, Seat> seats;

    public Theatre(String id, String name) {
        this.id = id;
        this.name = name;
        this.seats = new HashMap<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Map<String, Seat> getSeats() { return seats; }
    
    public void addSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }
}
