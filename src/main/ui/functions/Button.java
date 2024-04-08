package ui.functions;

import ui.TheatreAppGUI;

import javax.swing.*;

// abstract class for buttons used in TheatreAppGUI
public abstract class Button extends JButton {
    protected JButton button;
    protected TheatreAppGUI app;

    // EFFECTS: constructs a JButton in the specified panel with given text and enabled set to status
    public Button(JPanel panel, boolean status, TheatreAppGUI app, String text) {
        this.app = app;
        button = new JButton(text);
        setEnabled(status);
        panel.add(button);
    }

    public void setEnabled(boolean b) {
        button.setEnabled(b);
    }
}
