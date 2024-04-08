package persistence;

import model.Movie;
import model.Ticket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMovie(String title, String rating, String summary, int runLength, String staringActors,
                              String director, String genre, Movie movie) {
        assertEquals(title, movie.getTitle());
        assertEquals(rating, movie.getRating());
        assertEquals(summary, movie.getSummary());
        assertEquals(runLength, movie.getRunLength());
        assertEquals(staringActors, movie.getStaringActors());
        assertEquals(director, movie.getDirector());
        assertEquals(genre, movie.getGenre());
    }

    protected void checkTicket(String movieTitle, int index, Ticket ticket) {
        assertEquals(movieTitle, ticket.getMovieTitle());
        assertEquals(index, ticket.getMovieTime());
    }
}
