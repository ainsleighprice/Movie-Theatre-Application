package ui;

import model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// delegate class for TheatreAppGUI. Represents movie information display
public class MovieInfoDisplay {
    private TheatreAppGUI app;
    private JTextArea titleDisplay;
    private JTextArea ratingDisplay;
    private JTextArea summaryDisplay;
    private JTextArea runLengthDisplay;
    private JTextArea staringActorsDisplay;
    private JTextArea directorDisplay;
    private JTextArea genreDisplay;

    // EFFECTS: constructs movie info display in given area
    public MovieInfoDisplay(TheatreAppGUI app, JPanel area) {
        this.app = app;
        initializeTextArea(area);
        displayMovie();
    }

    // MODIFIES: this
    // EFFECTS: helper method that initializes textArea
    private void initializeTextArea(JPanel area) {
        JPanel textArea = new JPanel();
        textArea.setLayout(new GridLayout(0, 1));
        textArea.setSize(new Dimension(0, 0));

        area.add(textArea);

        titleDisplay = new JTextArea();
        textArea.add(titleDisplay);

        ratingDisplay = new JTextArea();
        textArea.add(ratingDisplay);

        summaryDisplay = new JTextArea();
        summaryDisplay.setLineWrap(true);
        summaryDisplay.setWrapStyleWord(true);
        textArea.add(summaryDisplay);

        runLengthDisplay = new JTextArea();
        textArea.add(runLengthDisplay);

        staringActorsDisplay = new JTextArea();
        textArea.add(staringActorsDisplay);

        directorDisplay = new JTextArea();
        textArea.add(directorDisplay);

        genreDisplay = new JTextArea();
        textArea.add(genreDisplay);
    }

    // MODIFIES: this
    // EFFECTS: if showing slot is not empty slot, displays movie information in textArea.
    //          Else, displays nothing
    public void displayMovie() {
        int index = app.getListShowings().getSelectedIndex();
        Movie m = app.getShowings().getMovieAtIndex(index);

        if (m != null) {
            List<String> info = m.getInformationStrings();
            titleDisplay.setText(info.get(0));
            ratingDisplay.setText(info.get(1));
            summaryDisplay.setText(info.get(2));
            runLengthDisplay.setText(info.get(3));
            staringActorsDisplay.setText(info.get(4));
            directorDisplay.setText(info.get(5));
            genreDisplay.setText(info.get(6));
        }
    }

    // MODIFIES: this
    // EFFECTS: sets text in each text area to an empty string
    public void resetTextArea() {
        titleDisplay.setText("");
        ratingDisplay.setText("");
        summaryDisplay.setText("");
        runLengthDisplay.setText("");
        staringActorsDisplay.setText("");
        directorDisplay.setText("");
        genreDisplay.setText("");
    }
}