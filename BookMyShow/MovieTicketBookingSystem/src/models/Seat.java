package models;

public class Seat {
    private String id;
    private SeatType type;
    private boolean isAvailable;

    public Seat(String id, SeatType type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true;
    }

    public String getId() { return id; }
    public SeatType getType() { return type; }
    
    public boolean isAvail() { return isAvailable; }
    public void setAvail(boolean available) { isAvailable = available; }
}
