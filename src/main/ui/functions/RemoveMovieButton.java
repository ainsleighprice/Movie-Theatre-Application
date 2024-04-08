package ui.functions;

import model.MovieTheatre;
import ui.TheatreAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// delegate class for TheatreAppGUI. Represents the remove movie button.
public class RemoveMovieButton extends Button {
    private static final String TEXT = "Remove Movie";
    private static final String TIME_TITLE_GAP = "---";

    // EFFECTS: constructs remove button according to super with RemoveMovieListener.
    public RemoveMovieButton(JPanel panel, boolean status, TheatreAppGUI app) {
        super(panel, status, app, TEXT);
        button.addActionListener(new RemoveMovieListener());
    }

    // Listener for the remove movie button
    class RemoveMovieListener implements ActionListener {
        private DefaultListModel<String> listModelShowings;

        // MODIFIES: this, app
        // EFFECTS: removes movie when remove movie button is pressed
        public void actionPerformed(ActionEvent e) {
            JList<MovieTheatre> listShowings = app.getListShowings();
            MovieTheatre showings = app.getShowings();
            listModelShowings = app.getListModelShowings();

            int index = listShowings.getSelectedIndex();
            setEmptySlot(index);
            showings.removeShowingAtTime(index);

            button.setEnabled(false);
            app.getAddNewMovieButton().setEnabled(true);
            app.getMovieInfoDisplay().resetTextArea();
            app.getBookTicketButton().setEnabled(false);
        }

        // MODIFIES: this
        // EFFECTS: sets object at i in listModelShowings to empty slot
        private void setEmptySlot(int i) {
            String timeString;
            int time = (i * 3) + 1;
            if (time < 10) {
                timeString = "0" + time + ":00";
            } else {
                timeString = time + ":00";
            }
            listModelShowings.set(i, timeString + TIME_TITLE_GAP + "Empty Slot");
        }
    }
}
