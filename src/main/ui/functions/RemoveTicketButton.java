package ui.functions;

import model.MyTickets;
import ui.TheatreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// delegate class for TheatreAppGUI. Represents the remove ticket button.
public class RemoveTicketButton extends Button {
    private static final String text = "Remove Ticket";

    // EFFECTS: constructs ticket button according to super with RemoveTicketListener.
    public RemoveTicketButton(JPanel panel, boolean status, TheatreAppGUI app) {
        super(panel, status, app, text);
        button.addActionListener(new RemoveTicketListener());
    }

    // Listener for the remove ticket button
    class RemoveTicketListener implements ActionListener {

        // MODIFIES: app
        // EFFECTS: removes ticket when remove button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            JList<MyTickets> listTickets = app.getListTickets();
            DefaultListModel<String> listModelTickets = app.getListModelTickets();
            MyTickets tickets = app.getTickets();

            int index = listTickets.getSelectedIndex();
            listModelTickets.remove(index);
            tickets.removeTicket(index);

            int size = listModelTickets.getSize();

            if (size != 0) {
                if (index == listModelTickets.getSize()) {
                    index--;
                }
                listTickets.setSelectedIndex(index);
                listTickets.ensureIndexIsVisible(index);
            }
        }
    }
}
