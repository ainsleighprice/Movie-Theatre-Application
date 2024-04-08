package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyTicketsTest {
    private MyTickets testTickets;
    private MovieTheatre theatre;

    @BeforeEach
    void runBefore() {
        testTickets = new MyTickets();
        theatre = new MovieTheatre();
    }

    @Test
    void testBookTicketMovie() {
        theatre.addNewMovie(0, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        assertTrue(testTickets.bookTicket(0, theatre));
        assertEquals(1, testTickets.numTickets());
    }

    @Test
    void testBookTicketNoMovie() {
        assertFalse(testTickets.bookTicket(4, theatre));
        assertEquals(0, testTickets.numTickets());
    }

    @Test
    void testBookTicketMultiple() {
        theatre.addNewMovie(6, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        theatre.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        theatre.addNewMovie(5, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");

        assertTrue(testTickets.bookTicket(6, theatre));
        assertEquals(1, testTickets.numTickets());
        assertTrue(testTickets.bookTicket(6, theatre));
        assertEquals(2, testTickets.numTickets());
        assertTrue(testTickets.bookTicket(5, theatre));
        assertEquals(3, testTickets.numTickets());
        assertFalse(testTickets.bookTicket(3, theatre));
        assertEquals(3, testTickets.numTickets());
    }

    @Test
    void testNumTickets() {
        theatre.addNewMovie(6, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        theatre.addNewMovie(7, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        theatre.addNewMovie(5, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTickets.bookTicket(6, theatre);
        testTickets.bookTicket(6, theatre);
        testTickets.bookTicket(7, theatre);
        testTickets.bookTicket(7, theatre);
        testTickets.bookTicket(5, theatre);

        assertEquals(5, testTickets.numTickets());
    }

    @Test
    void testListStringTickets() {
        theatre.addNewMovie(0, "Good", "PG", "This is my summary",
                120, "Bobby John", "Davie Brown", "comedy");
        testTickets.bookTicket(0, theatre);
        ArrayList<String> list = testTickets.listStringTickets();
        assertEquals(1, list.size());
    }

    @Test
    void testAddTicketEmpty() {
        Ticket t = new Ticket("test", 0);
        testTickets.addTicket(t);
        assertEquals(1, testTickets.numTickets());
        ArrayList tickets = testTickets.getTickets();
        assertEquals(t, tickets.get(0));
    }

    @Test
    void testAddTicketNotEmpty() {
        Ticket t1 = new Ticket("test", 0);
        testTickets.addTicket(t1);
        assertEquals(1, testTickets.numTickets());

        Ticket t2 = new Ticket("test", 4);
        testTickets.addTicket(t2);
        assertEquals(2, testTickets.numTickets());

        ArrayList tickets = testTickets.getTickets();
        assertEquals(t1, tickets.get(0));
        assertEquals(t2, tickets.get(1));
    }

    @Test
    void testRemoveTicket() {
        Ticket t1 = new Ticket("test", 0);
        Ticket t2 = new Ticket("test", 7);
        testTickets.addTicket(t1);
        testTickets.addTicket(t2);

        testTickets.removeTicket(1);
        assertEquals(1, testTickets.getTickets().size());
        assertEquals(t1, testTickets.getTickets().get(0));
        testTickets.removeTicket(0);
        assertEquals(0, testTickets.getTickets().size());
    }

}
