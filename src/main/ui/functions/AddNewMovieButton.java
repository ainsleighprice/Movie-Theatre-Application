package ui.functions;

import model.MovieTheatre;
import ui.TheatreAppGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// delegate class for TheatreAppGUI. Represents the add new movie button.
public class AddNewMovieButton extends Button {
    private static final String TEXT = "Add New Movie";
    private static final String TIME_TITLE_GAP = "---";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;

    // EFFECTS: constructs add new movie button according to super with NewMovieListener.
    public AddNewMovieButton(JPanel panel, boolean status, TheatreAppGUI app) {
        super(panel, status, app, TEXT);
        button.addActionListener(new NewMovieListener());
    }

    // Listener for add new movie button
    class NewMovieListener implements ActionListener {
        private JFrame newMovie;
        private JTextField titleField;
        private JTextField ratingField;
        private JTextField summaryField;
        private JTextField runLengthField;
        private JTextField staringActorsField;
        private JTextField directorField;
        private JTextField genreField;

        @Override
        // MODIFIES: this, app
        // EFFECTS: when button clicked, creates new JFrame window that allows user to add the new movie
        public void actionPerformed(ActionEvent e) {
            newMovie = new JFrame("New Movie");
            newMovie.setLayout(new BorderLayout());
            newMovie.setMinimumSize(new Dimension(WIDTH, HEIGHT));

            initializeTextFields();

            initializeButton();

            newMovie.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newMovie.setLocationRelativeTo(null);
            newMovie.setVisible(true);
        }

        // MODIFIES: this
        // EFFECTS: initializes text fields in new movie window with instructions
        private void initializeTextFields() {
            titleField = new JTextField("Type title here.");

            ratingField = new JTextField("Type rating here. Must be one of G, PG, 14A, 18A, R.");

            summaryField = new JTextField("Type summary here. Must be 250 characters or less.");

            runLengthField = new JTextField("Type the run length in minutes (eg. 60). It must be 180 minutes or less.");

            staringActorsField = new JTextField("Type name(s) of the staring actor(s) here.");

            directorField = new JTextField("Type name of the director here.");

            genreField = new JTextField("Type the genre here.");

            initializeTextFieldsGraphic();
        }

        // MODIFIES: this
        // EFFECTS: initializes now JPanel and adds all text fields to it
        private void initializeTextFieldsGraphic() {
            JPanel fieldArea = new JPanel();
            fieldArea.setLayout(new GridLayout(0, 1));
            fieldArea.setSize(new Dimension(0, 0));
            newMovie.add(fieldArea, BorderLayout.CENTER);

            fieldArea.add(titleField);
            fieldArea.add(ratingField);
            fieldArea.add(summaryField);
            fieldArea.add(runLengthField);
            fieldArea.add(staringActorsField);
            fieldArea.add(directorField);
            fieldArea.add(genreField);
        }

        // MODIFIES: this
        // EFFECTS: initializes add button
        private void initializeButton() {
            JButton addButton = new JButton("Add");
            addButton.addActionListener(new AddListener());

            newMovie.add(addButton, BorderLayout.SOUTH);
        }

        // Listener for add button
        class AddListener implements ActionListener {
            private MovieTheatre showings;
            private int index;
            private String title;
            private String rating;
            private String summary;
            private int runLength;
            private String staringActors;
            private String director;
            private String genre;

            @Override
            // MODIFIES: this, app
            // EFFECTS: tries to add new movie when button is clicked
            public void actionPerformed(ActionEvent e) {
                JList<MovieTheatre> listShowings = app.getListShowings();
                showings = app.getShowings();
                DefaultListModel<String> listModelShowings = app.getListModelShowings();

                index = listShowings.getSelectedIndex();

                String title = titleField.getText();

                if (tryToAddNewMovie()) {
                    String timeString;
                    int time = (index * 3) + 1;
                    if (time < 10) {
                        timeString = "0" + time + ":00";
                    } else {
                        timeString = time + ":00";
                    }
                    listModelShowings.setElementAt(timeString + TIME_TITLE_GAP + title, index);

                    newMovie.setVisible(false);
                    app.getRemoveMovieButton().setEnabled(true);
                    app.getMovieInfoDisplay().displayMovie();
                    button.setEnabled(false);
                }


            }

            // MODIFIES: this
            // EFFECTS: if rating, summary and run length are all valid, adds new movie with info from text fields
            //          and returns true. Else calls methods to deal with what isn't valid and returns false.
            private boolean tryToAddNewMovie() {
                getTextFromFields();

                if (validRating(rating) && validSummary(summary) && validRunLength(runLength)) {
                    showings.addNewMovie(index, title, rating, summary, runLength, staringActors, director, genre);
                    return true;
                } else {
                    if (!validRating(rating)) {
                        notValid(ratingField, "Rating is not valid!");
                    }
                    if (!validSummary(summary)) {
                        notValid(summaryField, "Summary too long!");
                    }
                    if (!validRunLength(runLength)) {
                        notValid(runLengthField, "Run length not valid!");
                    }
                    return false;
                }
            }

            // MODIFIES: this
            // EFFECTS: gets text from fields and puts it into corresponding field of type string or int
            private void getTextFromFields() {
                title = titleField.getText();
                rating = ratingField.getText();
                summary = summaryField.getText();
                runLength = 0;
                try {
                    runLength = Integer.parseInt(runLengthField.getText());
                } catch (NumberFormatException ex) {
                    notValid(runLengthField, "Run length not valid!");
                }
                staringActors = staringActorsField.getText();
                director = directorField.getText();
                genre = genreField.getText();
            }

            // EFFECTS: returns true if rating is G, PG, 14A, 18A or R. Else returns false.
            private boolean validRating(String rating) {
                return rating.equals("G") || rating.equals("PG") || rating.equals("14A") || rating.equals("18A")
                        || rating.equals("R");
            }

            // EFFECTS: returns true if summary is 250 characters or fewer. Else returns false.
            private boolean validSummary(String summary) {
                return summary.length() <= 250;
            }

            // EFFECTS: returns true if runLength is less than or equal to 180. Else returns false.
            private boolean validRunLength(int runLength) {
                return runLength <= 180;
            }

            // MODIFIES: this
            // EFFECTS: sets field text to text
            private void notValid(JTextField field, String text) {
                field.setText(text);
            }
        }
    }
}
