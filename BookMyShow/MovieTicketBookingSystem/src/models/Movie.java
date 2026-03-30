package models;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    private String name;
    private String lang;
    private Map<String, Show> shows;

    public Movie(String name, String lang) {
        this.name = name;
        this.lang = lang;
        this.shows = new HashMap<>();
    }

    public String getName() { return name; }
    public String getLang() { return lang; }

    public void addMovieShow(Show show) {
        shows.put(show.getId(), show);
    }
    
    public Map<String, Show> getShows() {
        return shows;
    }
}
