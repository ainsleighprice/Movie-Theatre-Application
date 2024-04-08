package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTheatreTest {
    private MovieTheatre testTheatre;
    private MovieTheatre testTheatreBig;
    private MovieTheatre testTheatre1;

    @BeforeEach
    void runBefore() {
        testTheatre = new MovieTheatre();

        testTheatre1 = new MovieTheatre();
        testTheatre1.addNewMovie(0, "Horse", "14A", "Summary, summary, "
                + "summary",180, "John", "Davie Brown", "comedy");

        testTheatreBig = new MovieTheatre();
        testTheatreBig.addNewMovie(0, "Horse", "14A", "Summary, summary, " +
                "summary",180, "John", "Davie Brown", "comedy");
        testTheatreBig.addNewMovie(4, "Cow", "14A", "Summary, summary, " +
                "summary",180, "John", "Davie Brown", "comedy");
        testTheatreBig.addNewMovie(6, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTheatreBig.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTheatreBig.addNewMovie(5, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
    }

    @Test
    void testAddNewMovieAtAvailableTime() {
        assertTrue(testTheatre.addNewMovie(0, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy"));
        assertTrue(testTheatre.movieAtTime(0));
    }

    @Test
    void testAddNewMovieAtTakenTime() {
        testTheatre.addNewMovie(4, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        assertFalse(testTheatre.addNewMovie(4, "Truck", "R", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy"));
    }

    @Test
    void testAddMultipleMovies() {
        boolean theatre1 = testTheatre.addNewMovie(0, "Horse", "14A", "Summary, summary, " +
                        "summary",180, "John", "Davie Brown", "comedy");
        boolean theatre2 = testTheatre.addNewMovie(4, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        boolean theatre3 = testTheatre.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        boolean theatre4 = testTheatre.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        assertTrue(theatre1);
        assertTrue(theatre2);
        assertTrue(theatre3);
        assertFalse(theatre4);

        boolean theatre1Verify = testTheatre.movieAtTime(0);
        boolean theatre2Verify = testTheatre.movieAtTime(4);
        boolean theatre3Verify = testTheatre.movieAtTime(7);
        assertTrue(theatre1Verify);
        assertTrue(theatre2Verify);
        assertTrue(theatre3Verify);
    }

    @Test
    void testRemoveMovieExists() {
        assertTrue(testTheatre1.removeMovie("Horse"));
        assertFalse(testTheatre1.movieAtTime(0));
    }

    @Test
    void testRemoveMovieNoMovies() {
        assertFalse(testTheatre.removeMovie("Pig"));
    }

    @Test
    void testRemoveMovieWrongTitle() {
        assertFalse(testTheatre1.removeMovie("cat"));
        assertTrue(testTheatre1.movieAtTime(0));
    }

    @Test
    void testRemoveMultipleMovies() {
        assertTrue(testTheatreBig.removeMovie("Horse"));
        assertTrue(testTheatreBig.removeMovie("Good"));
        assertFalse(testTheatreBig.removeMovie("Good"));

        assertFalse(testTheatreBig.movieAtTime(0));
        assertFalse(testTheatreBig.movieAtTime(6));
        assertFalse(testTheatreBig.movieAtTime(5));
        assertFalse(testTheatreBig.movieAtTime(7));

        assertTrue(testTheatreBig.movieAtTime(4));
    }

    @Test
    void testRemoveShowingAtTimeExists() {
        assertTrue(testTheatre1.removeShowingAtTime(0));
        assertFalse(testTheatre1.movieAtTime(0));
    }

    @Test
    void testRemoveShowingAtTimeNotExist() {
        assertFalse(testTheatre.removeShowingAtTime(4));
    }

    @Test
    void testRemoveMultipleShowingAtTime() {
        assertTrue(testTheatreBig.removeShowingAtTime(0));
        assertTrue(testTheatreBig.removeShowingAtTime(4));
        assertTrue(testTheatreBig.removeShowingAtTime(7));
        assertTrue(testTheatreBig.removeShowingAtTime(5));

        assertFalse(testTheatreBig.removeShowingAtTime(3));

        assertFalse(testTheatreBig.movieAtTime(0));
        assertFalse(testTheatreBig.movieAtTime(4));
        assertFalse(testTheatreBig.movieAtTime(7));
        assertFalse(testTheatreBig.movieAtTime(5));

        assertTrue(testTheatreBig.movieAtTime(6));
    }

    @Test
    void testCurrentMovieTitlesNoMovies() {
        assertEquals("Sorry, no movies in theatre.", testTheatre.currentMovieTitles());
    }

    @Test
    void testCurrentMovieTitlesOneMovie() {
        assertEquals("Horse", testTheatre1.currentMovieTitles());
    }

    @Test
    void testCurrentMovieTitlesMultiple() {
        assertEquals("Horse, Cow, Good", testTheatreBig.currentMovieTitles());
    }
    @Test
    void testMovieTimesOne() {
        assertEquals("1:00", testTheatre1.movieTimes("Horse"));
    }

    @Test
    void testMovieTimesMultiple() {
        testTheatre.addNewMovie(6, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTheatre.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTheatre.addNewMovie(5, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");

        assertEquals("16:00, 19:00, 22:00", testTheatre.movieTimes("Good"));
    }

    @Test
    void testMovieTimesNone() {
        assertEquals("Sorry, this movie is not in theatre.", testTheatre.movieTimes("Chicken"));
    }

    @Test
    void testMovieTimesMixed() {
        assertEquals("1:00", testTheatreBig.movieTimes("Horse"));
        assertEquals("16:00, 19:00, 22:00", testTheatreBig.movieTimes("Good"));
        assertEquals("Sorry, this movie is not in theatre.", testTheatreBig.movieTimes("Chicken"));
    }

    @Test
    void testGetMovieInformationNone() {
        assertTrue(testTheatre.getMovieInformation("Crow").isEmpty());
    }

    @Test
    void testGetMovieInformationWrongTitle() {
        assertTrue(testTheatre1.getMovieInformation("Cow").isEmpty());
    }

    @Test
    void testGetMovieInformationCorrectOne() {
        assertEquals(testTheatre1.getMovieAtIndex(0).getInformationStrings(),
                testTheatre1.getMovieInformation("Horse"));
    }

    @Test
    void testGetMovieInformationMixed() {
        testTheatre.addNewMovie(0, "Horse", "14A", "Summary, summary, " +
                "summary",180, "John", "Davie Brown", "comedy");
        testTheatre.addNewMovie(4, "Horse", "14A", "Summary, summary, " +
                "summary",180, "John", "Davie Brown", "comedy");
        testTheatre.addNewMovie(6, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");

        assertEquals(testTheatre1.getMovieAtIndex(0).getInformationStrings(),
                testTheatre1.getMovieInformation("Horse"));

        assertTrue(testTheatre.getMovieInformation("Crow").isEmpty());
    }

    @Test
    void testGetMovieAtIndexMovie() {
        Movie m = testTheatreBig.getMovieAtIndex(6);
        assertEquals("Good", m.getTitle());
        assertEquals("PG", m.getRating());
        assertEquals("This is my summary", m.getSummary());
        assertEquals(120, m.getRunLength());
        assertEquals("Bobby John", m.getStaringActors());
        assertEquals("Davie Brown", m.getDirector());
        assertEquals("comedy", m.getGenre());
    }

    @Test
    void testGetMovieAtIndexNull() {
        assertEquals(null, testTheatre.getMovieAtIndex(2));
    }
}
