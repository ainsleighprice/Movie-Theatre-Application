package persistence;

import model.Movie;
import model.MovieTheatre;
import model.MyTickets;
import model.Ticket;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MovieTheatre mt = reader.readMovieTheatre();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMovieTheatreNoTickets() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMovieTheatreNoTickets.json");
        try {
            MovieTheatre mt = reader.readMovieTheatre();
            for (int i = 0; i < 8; i++) {
                Movie m = mt.getMovieAtIndex(i);
                assertEquals(null, m);
            }
            MyTickets ts = reader.readMyTickets();
            assertEquals(0, ts.numTickets());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMovieTheatreNoTickets() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMovieTheatreNoTickets.json");
        try {
            MovieTheatre mt = reader.readMovieTheatre();
            ArrayList<Movie> movies = mt.getShowings();
            int numMovies = 0;
            for (Movie m : movies) {
                if (m != null) {
                    numMovies++;
                }
            }
            assertEquals(2, numMovies);
            checkMovie("Horse", "14A", "Summary, summary, " +
                    "summary", 180, "John", "Davie Brown", "comedy", movies.get(0));
            checkMovie("Cow", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy", movies.get(4));

            MyTickets ts = reader.readMyTickets();
            assertEquals(0, ts.numTickets());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMovieTheatreAndTickets() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMovieTheatreAndTickets.json");
        try {
            MovieTheatre mt = reader.readMovieTheatre();
            ArrayList<Movie> movies = mt.getShowings();
            int numMovies = 0;
            for (Movie m : movies) {
                if (m != null) {
                    numMovies++;
                }
            }
            assertEquals(2, numMovies);
            checkMovie("Horse", "14A", "Summary, summary, " +
                    "summary", 180, "John", "Davie Brown", "comedy", movies.get(0));
            checkMovie("Cow", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy", movies.get(4));

            MyTickets ts = reader.readMyTickets();
            ArrayList<Ticket> tickets = ts.getTickets();
            assertEquals(2, ts.numTickets());
            checkTicket("Horse", 0, tickets.get(0));
            checkTicket("Cow", 4, tickets.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
