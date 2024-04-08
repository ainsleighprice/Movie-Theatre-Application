package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMovie = new Movie("Good Movie", "PG", "This is my summary", 120,
                "Bob, Karen, Dave", "Jim", "Horror");
    }

    @Test
    void testConstructor() {
        assertEquals("Good Movie", testMovie.getTitle());
        assertEquals("PG", testMovie.getRating());
        assertEquals("This is my summary", testMovie.getSummary());
        assertEquals(120, testMovie.getRunLength());
        assertEquals("Bob, Karen, Dave", testMovie.getStaringActors());
        assertEquals("Jim", testMovie.getDirector());
        assertEquals("Horror", testMovie.getGenre());
    }

    @Test
    void testGetInformation() {
        List<String> infoList = testMovie.getInformationStrings();
        assertEquals(7, infoList.size());

        assertEquals("Title: Good Movie", infoList.get(0));
        assertEquals("Rating: PG", infoList.get(0));
        assertEquals("Summary: This is my summary", infoList.get(0));
        assertEquals("Run Length: 120 minutes", infoList.get(0));
        assertEquals("Staring Actors: Bob, Karen, Dave", infoList.get(0));
        assertEquals("Director: Jim", infoList.get(0));
        assertEquals("Genre: Horror", infoList.get(0));
    }
}
