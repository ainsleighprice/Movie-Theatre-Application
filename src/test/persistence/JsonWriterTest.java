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
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            MovieTheatre mt = new MovieTheatre();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMovieTheatreNoTickets() {
        try {
            MovieTheatre mt = new MovieTheatre();
            MyTickets ts = new MyTickets();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMovieTheatreNoTickets.json");
            writer.open();
            writer.write(mt, ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMovieTheatreNoTickets.json");
            mt = reader.readMovieTheatre();
            for (int i = 0; i < 8; i++) {
                Movie m = mt.getMovieAtIndex(i);
                assertEquals(null, m);
            }
            ts = reader.readMyTickets();
            assertEquals(0, ts.numTickets());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMovieTheatreNoTickets() {
        try {
            MovieTheatre mt = new MovieTheatre();
            mt.addNewMovie(0, "Horse", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy");
            mt.addNewMovie(4, "Cow", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy");
            MyTickets ts = new MyTickets();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovieTheatreNoTickets.json");
            writer.open();
            writer.write(mt, ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMovieTheatreNoTickets.json");
            mt = reader.readMovieTheatre();
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

            ts = reader.readMyTickets();
            assertEquals(0, ts.numTickets());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMovieTheatreAndTickets() {
        try {
            MovieTheatre mt = new MovieTheatre();
            mt.addNewMovie(0, "Horse", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy");
            mt.addNewMovie(4, "Cow", "14A", "Summary, summary, " +
                    "summary",180, "John", "Davie Brown", "comedy");
            MyTickets ts = new MyTickets();
            ts.bookTicket(0, mt);
            ts.bookTicket(4, mt);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMovieTheatreAndTickets.json");
            writer.open();
            writer.write(mt, ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMovieTheatreAndTickets.json");
            mt = reader.readMovieTheatre();
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

            ts = reader.readMyTickets();
            ArrayList<Ticket> tickets = ts.getTickets();
            assertEquals(2, ts.numTickets());
            checkTicket("Horse", 0, tickets.get(0));
            checkTicket("Cow", 4, tickets.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
