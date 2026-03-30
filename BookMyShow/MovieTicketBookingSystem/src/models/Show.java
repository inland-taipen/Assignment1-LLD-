package models;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

public class Show {
    private String id;
    private LocalDateTime time;
    private Theatre theatre;
    private Movie movie;
    private Map<String, Seat> showSeats;

    public Show(String id, LocalDateTime time, Theatre theatre, Movie movie) {
        this.id = id;
        this.time = time;
        this.theatre = theatre;
        this.movie = movie;
        this.showSeats = new HashMap<>();
        // Deep copy seats from theatre for this show to maintain individual availability states
        for (Map.Entry<String, Seat> entry : theatre.getSeats().entrySet()) {
            Seat originalSeat = entry.getValue();
            this.showSeats.put(originalSeat.getId(), new Seat(originalSeat.getId(), originalSeat.getType()));
        }
    }

    public String getId() { return id; }
    public LocalDateTime getTime() { return time; }
    public Theatre getTheatre() { return theatre; }
    public Movie getMovie() { return movie; }

    public Map<String, Seat> getShowSeats() {
        return showSeats;
    }
    
    public void addMovieShow() {
        // Additional setup or registration can occur here
    }
}
