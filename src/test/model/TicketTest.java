package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {
    private Ticket testTicket;

    @BeforeEach
    void runBefore() {
        testTicket = new Ticket("Title", 2);
    }

    @Test
    void testConstructor() {
        assertEquals("Title", testTicket.getMovieTitle());
        assertEquals(2, testTicket.getMovieTime());
    }

    @Test
    void testStringTicket() {
        assertEquals("Title at 7:00", testTicket.stringTicket());
    }
}
