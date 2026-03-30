package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String name;
    private Map<String, Theatre> theatres;
    private Map<String, Movie> movies;

    public City(String name) {
        this.name = name;
        this.theatres = new HashMap<>();
        this.movies = new HashMap<>();
    }

    public String getName() { return name; }

    public void addTheatre(Theatre t) { theatres.put(t.getId(), t); }
    public void addMovie(Movie m) { movies.put(m.getName(), m); }

    public List<Movie> getMovie() {
        return new ArrayList<>(movies.values());
    }

    public List<Theatre> getTheatre() {
        return new ArrayList<>(theatres.values());
    }
}
