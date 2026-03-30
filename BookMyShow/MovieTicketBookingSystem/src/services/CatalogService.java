package services;

import models.*;
import java.util.*;
import java.time.LocalDateTime;

public class CatalogService {
    private Map<String, City> cities;
    private Map<String, Show> shows;

    public CatalogService() {
        cities = new HashMap<>();
        shows = new HashMap<>();
    }

    private void requireAdmin(User user) {
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new SecurityException("Access Denied: Only ADMIN accounts can perform this operation.");
        }
    }

    public void addCity(User admin, City city) {
        requireAdmin(admin);
        cities.put(city.getName(), city);
    }
    
    public void addMovie(User admin, String cityName, Movie movie) {
        requireAdmin(admin);
        City city = cities.get(cityName);
        if (city != null) city.addMovie(movie);
    }

    public Theatre addTheatre(User admin, String cityName, String theatreId, String theatreName) {
        requireAdmin(admin);
        City city = cities.get(cityName);
        if (city != null) {
            Theatre theatre = new Theatre(theatreId, theatreName);
            city.addTheatre(theatre);
            return theatre;
        }
        return null;
    }

    public Show addMovieShow(User admin, String showId, Movie movie, Theatre theatre, LocalDateTime time) {
        requireAdmin(admin);
        Show show = new Show(showId, time, theatre, movie);
        shows.put(showId, show);
        movie.addMovieShow(show);
        return show;
    }

    public List<Theatre> showTheater(String cityName) {
        City city = cities.get(cityName);
        if (city != null) return city.getTheatre();
        return Collections.emptyList();
    }

    public List<Movie> showMovie(String cityName) {
        City city = cities.get(cityName);
        if (city != null) return city.getMovie();
        return Collections.emptyList();
    }

    public Show getShow(String showId) {
        return shows.get(showId);
    }
}
