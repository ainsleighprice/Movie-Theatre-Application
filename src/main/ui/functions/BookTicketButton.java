package ui.functions;

import model.MovieTheatre;
import model.MyTickets;
import model.Ticket;
import ui.TheatreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// delegate class for TheatreAppGUI. Represents the book ticket button.
public class BookTicketButton extends Button {
    private static final String TEXT = "Book Ticket";

    // EFFECTS: constructs book ticket button according to super with BookTicketListener.
    public BookTicketButton(JPanel panel, boolean status, TheatreAppGUI app) {
        super(panel, status, app, TEXT);
        button.addActionListener(new BookTicketListener());
    }

    // Listener for the book ticket button
    class BookTicketListener implements ActionListener {

        // MODIFIES: app
        // EFFECTS: books ticket when book ticket button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> listModelTickets = app.getListModelTickets();
            JList<MovieTheatre> listShowings = app.getListShowings();
            MyTickets tickets = app.getTickets();
            MovieTheatre showings = app.getShowings();

            int indexMovie = listShowings.getSelectedIndex();

            tickets.bookTicket(indexMovie, showings);

            int indexTicket = tickets.numTickets() - 1;
            Ticket justAdded = tickets.getTickets().get(indexTicket);

            listModelTickets.addElement("Ticket: " + justAdded.stringTicket());
        }
    }
}
