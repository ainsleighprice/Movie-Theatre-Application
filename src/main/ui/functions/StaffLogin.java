package ui.functions;

import ui.TheatreAppGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// delegate class for TheatreAppGUI. Represents staff login feature (both button and text field)
public class StaffLogin extends Button {
    private static final String TEXT = "Staff Login";
    private JTextField passwordEntry;

    public StaffLogin(JPanel panel, boolean status, TheatreAppGUI app) {
        super(panel, status, app, TEXT);
        passwordEntry = new JTextField("Enter staff password here.");

        StaffLoginListener staffLoginListener = new StaffLoginListener();
        passwordEntry.addActionListener(staffLoginListener);
        passwordEntry.getDocument().addDocumentListener(staffLoginListener);

        button.addActionListener(staffLoginListener);

        panel.add(passwordEntry);
    }

    // Listener for staff login button and corresponding text field
    class StaffLoginListener implements ActionListener, DocumentListener {

        private static final String STAFF_PASSWORD = "Password";

        // MODIFIES: this, app
        // EFFECTS: enables remove movie and add new movie buttons when staff login button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            RemoveMovieButton removeMovieButton = app.getRemoveMovieButton();
            AddNewMovieButton addNewMovieButton = app.getAddNewMovieButton();

            app.setLoggedIn(true);
            removeMovieButton.setEnabled(app.showingSlotNotEmpty());
            addNewMovieButton.setEnabled(!app.showingSlotNotEmpty());
            button.setEnabled(false);
            passwordEntry.setText("Logged into staff.");
            passwordEntry.setEnabled(false);
        }

        // MODIFIES: this
        // EFFECTS: enables staff login button if passwordEntry text is STAFF_PASSWORD
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (passwordEntry.getText().equals(STAFF_PASSWORD)) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: disables staff login button if passwordEntry is empty
        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
            }
        }

        // MODIFIES: this
        // EFFECTS: enables staff login button if passwordEntry text is STAFF_PASSWORD
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (passwordEntry.getText().equals(STAFF_PASSWORD)) {
                button.setEnabled(true);
            }
        }
    }

}
