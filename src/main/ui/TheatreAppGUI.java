package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.functions.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Ainsleigh's 24hour GUI theatre application
public class TheatreAppGUI extends JFrame implements ListSelectionListener {
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 1000;
    private static final int LOAD_SAVE_WIDTH = 500;
    private static final int LOAD_SAVE_HEIGHT = 200;
    private static final String JSON_STORE = "./data/movieTheatreApplication.json";

    private MovieInfoDisplay movieInformationDisplay;
    private JList<MovieTheatre> listShowings;
    private JList<MyTickets> listTickets;
    private DefaultListModel<String> listModelShowings;
    private DefaultListModel<String> listModelTickets;
    private RemoveMovieButton removeMovieButton;
    private RemoveTicketButton removeTicketButton;
    private BookTicketButton bookTicketButton;
    private AddNewMovieButton addNewMovieButton;
    private JFrame loadShowings;
    private JFrame saveShowings;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean loggedIn;
    private MovieTheatre showings;
    private MyTickets tickets;

    private static final String TIME_TITLE_GAP = "---";

    // EFFECTS: runs the theatre application
    public TheatreAppGUI() {
        super("Movie Theatre");
        initializeFields();
        promptLoad();
    }

    // getters
    public MovieTheatre getShowings() {
        return showings;
    }

    public MyTickets getTickets() {
        return tickets;
    }

    public JList<MovieTheatre> getListShowings() {
        return listShowings;
    }

    public JList<MyTickets> getListTickets() {
        return listTickets;
    }

    public DefaultListModel<String> getListModelShowings() {
        return listModelShowings;
    }

    public DefaultListModel<String> getListModelTickets() {
        return listModelTickets;
    }

    public RemoveMovieButton getRemoveMovieButton() {
        return removeMovieButton;
    }

    public AddNewMovieButton getAddNewMovieButton() {
        return addNewMovieButton;
    }

    public BookTicketButton getBookTicketButton() {
        return bookTicketButton;
    }

    public MovieInfoDisplay getMovieInfoDisplay() {
        return movieInformationDisplay;
    }

    // setters
    public void setLoggedIn(boolean b) {
        loggedIn = b;
    }

    // MODIFIES: this
    // EFFECTS: initializes showings, tickets, jsonWriter, jsonReader and loggedIn fields
    private void initializeFields() {
        showings = new MovieTheatre();
        tickets = new MyTickets();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        loggedIn = false;
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window where main window for TheatreAppGUI, and adds the centre display, the list of
    //          showings graphic, the list of tickets graphic and the buttons used to manipulate the tickets and
    //          showings.
    private void initializeMainWindowGraphics() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));

        initializeShowingsGraphic();
        initializeTicketsGraphic();

        initializeButtons();

        initializeCentreDisplay();

        setDefaultCloseOperation(promptSave());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Image (./images/Movie_Image.jpeg) retrieved from this website
    // Link: https://www.freecreatives.com/icons/movie-icon-designs.html
    // MODIFIES: this
    // EFFECTS: initializes centre display with movie information display and an image
    private void initializeCentreDisplay() {
        JPanel centreDisplay = new JPanel();
        centreDisplay.setLayout(new GridLayout(1, 2));
        centreDisplay.setSize(new Dimension(0, 0));

        add(centreDisplay, BorderLayout.CENTER);

        movieInformationDisplay = new MovieInfoDisplay(this, centreDisplay);

        ImageIcon movieImage = new ImageIcon("./images/Movie_Image.jpeg");
        JLabel movieImageAsLabel = new JLabel(movieImage);
        centreDisplay.add(movieImageAsLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates new JFrame window that prompts user to load showings
    private void promptLoad() {
        loadShowings = new JFrame("Load Showings From File");
        loadShowings.setLayout(new BorderLayout());
        loadShowings.setMinimumSize(new Dimension(LOAD_SAVE_WIDTH, LOAD_SAVE_HEIGHT));

        JButton loadShowingsButton = new JButton("Load Showings");
        loadShowingsButton.setEnabled(true);
        loadShowingsButton.addActionListener(new LoadShowingsListener(loadShowingsButton));

        JButton dontLoadShowingsButton = new JButton("Don't Load Showings");
        dontLoadShowingsButton.setEnabled(true);
        dontLoadShowingsButton.addActionListener(new LoadShowingsListener(dontLoadShowingsButton));

        loadShowings.add(loadShowingsButton, BorderLayout.WEST);
        loadShowings.add(dontLoadShowingsButton, BorderLayout.EAST);

        loadShowings.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loadShowings.setLocationRelativeTo(null);
        loadShowings.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates new JFrame window that prompts user to save showings and tickets. returns
    //          JFrame.DO_NOTHING_ON_CLOSE
    private int promptSave() {
        saveShowings = new JFrame("Save Showings To File");
        saveShowings.setLayout(new BorderLayout());
        saveShowings.setMinimumSize(new Dimension(LOAD_SAVE_WIDTH, LOAD_SAVE_HEIGHT));

        JButton saveShowingsButton = new JButton("Save Showings");
        saveShowingsButton.setEnabled(true);
        saveShowingsButton.addActionListener(new SaveShowingsListener(saveShowingsButton));

        JButton dontSaveShowingsButton = new JButton("Don't Save Showings");
        dontSaveShowingsButton.setEnabled(true);
        dontSaveShowingsButton.addActionListener(new SaveShowingsListener(dontSaveShowingsButton));

        saveShowings.add(saveShowingsButton, BorderLayout.WEST);
        saveShowings.add(dontSaveShowingsButton, BorderLayout.EAST);

        saveShowings.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        saveShowings.setLocationRelativeTo(null);
        saveShowings.setVisible(true);

        return JFrame.DISPOSE_ON_CLOSE;
    }

    // MODIFIES: this
    // EFFECTS: a helper method which initializes the showings graphic
    private void initializeShowingsGraphic() {
        listModelShowings = new DefaultListModel<>();
        addShowingsToListModelShowings();

        listShowings = new JList(listModelShowings);
        listShowings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listShowings.setSelectedIndex(0);
        listShowings.addListSelectionListener(this);
        listShowings.setVisibleRowCount(8);

        JScrollPane listScrollPaneShowings = new JScrollPane(listShowings);
        add(listScrollPaneShowings, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: a helper method which initializes the tickets graphic
    private void initializeTicketsGraphic() {
        listModelTickets = new DefaultListModel<>();
        addTicketsToListModelTickets();

        listTickets = new JList(listModelTickets);
        listTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTickets.setSelectedIndex(0);
        listTickets.addListSelectionListener(this);
        listTickets.setVisibleRowCount(5);

        JScrollPane listScrollPaneTickets = new JScrollPane(listTickets);
        add(listScrollPaneTickets, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: a helper method which adds the buttonArea to the functionArea, and the buttons to the buttonArea.
    private void initializeButtons() {
        JPanel functionArea = new JPanel();
        functionArea.setLayout(new GridLayout(0, 2));
        functionArea.setSize(new Dimension(0, 0));
        add(functionArea, BorderLayout.SOUTH);

        new StaffLogin(functionArea, false, this);
        bookTicketButton = new BookTicketButton(functionArea, showingSlotNotEmpty(), this);
        removeMovieButton = new RemoveMovieButton(functionArea, false, this);
        removeTicketButton = new RemoveTicketButton(functionArea, tickets.numTickets() != 0, this);
        addNewMovieButton = new AddNewMovieButton(functionArea, false, this);
    }

    // MODIFIES: this
    // EFFECTS: creates showing schedule by adding showings to the list model for showings
    private void addShowingsToListModelShowings() {
        int index = 0;
        String timeString;
        for (Movie m : showings.getShowings()) {
            int time = (index * 3) + 1;
            if (time < 10) {
                timeString = "0" + time + ":00";
            } else {
                timeString = time + ":00";
            }
            if (m != null) {
                listModelShowings.addElement(timeString + TIME_TITLE_GAP + m.getTitle());
            } else {
                listModelShowings.addElement(timeString + TIME_TITLE_GAP + "Empty Slot");
            }
            index++;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds tickets to the list model for tickets
    private void addTicketsToListModelTickets() {
        for (Ticket t : tickets.getTickets()) {
            listModelTickets.addElement("Ticket: " + t.stringTicket());
        }
    }

    private void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: updates required buttons and movie display when selection for listShowings or listTickets changes
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (loggedIn) {
                if (listShowings.getSelectedIndex() == -1) {
                    removeMovieButton.setEnabled(false);
                    addNewMovieButton.setEnabled(false);
                } else {
                    removeMovieButton.setEnabled(showingSlotNotEmpty());
                    addNewMovieButton.setEnabled(!showingSlotNotEmpty());
                }
            }

            if (listShowings.getSelectedIndex() == -1) {
                bookTicketButton.setEnabled(false);
            } else {
                bookTicketButton.setEnabled(showingSlotNotEmpty());
            }

            removeTicketButton.setEnabled(listTickets.getSelectedIndex() != -1);

            if (listShowings.getSelectedIndex() == -1) {
                movieInformationDisplay.resetTextArea();
            } else {
                movieInformationDisplay.resetTextArea();
                movieInformationDisplay.displayMovie();
            }

        }
    }

    // EFFECTS: returns true if selected showing slot isn't empty
    public boolean showingSlotNotEmpty() {
        int indexMovie = listShowings.getSelectedIndex();
        Movie m = showings.getMovieAtIndex(indexMovie);
        return m != null;
    }

    // listener for both buttons in load showings window
    class LoadShowingsListener implements ActionListener {
        private JButton button;
        private JFrame loadTickets;

        // MODIFIES: this
        // EFFECTS: sets this.button to button
        public LoadShowingsListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: if button pressed is load showings button, creates new JFrame for load tickets. Else sets load
        //          showings window visibility to false and initializes main window.
        @Override
        public void actionPerformed(ActionEvent e) {
            loadShowings.setVisible(false);

            if ("Load Showings".equals(button.getText())) {
                loadShowings();

                loadTickets = new JFrame("Load Tickets From File");
                loadTickets.setLayout(new BorderLayout());
                loadTickets.setMinimumSize(new Dimension(LOAD_SAVE_WIDTH, LOAD_SAVE_HEIGHT));

                JButton loadTicketsButton = new JButton("Load Tickets");
                loadTicketsButton.setEnabled(true);
                loadTicketsButton.addActionListener(new LoadTicketsListener(loadTicketsButton));
                loadTickets.add(loadTicketsButton, BorderLayout.WEST);

                JButton dontLoadTicketsButton = new JButton("Don't Load Tickets");
                dontLoadTicketsButton.setEnabled(true);
                dontLoadTicketsButton.addActionListener(new LoadTicketsListener(dontLoadTicketsButton));
                loadTickets.add(dontLoadTicketsButton, BorderLayout.EAST);

                loadTickets.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                loadTickets.setLocationRelativeTo(null);
                loadTickets.setVisible(true);
            } else {
                initializeMainWindowGraphics();
            }
        }

        // This method references code from this repo
        // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        // MODIFIES: showings
        // EFFECTS: reads showings from json file and sets them as showings
        private void loadShowings() {
            try {
                showings = jsonReader.readMovieTheatre();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // listener for both buttons in load tickets window
        class LoadTicketsListener implements ActionListener {
            private JButton button;

            // MODIFIES: this
            // EFFECTS: sets this.button to button
            public LoadTicketsListener(JButton button) {
                this.button = button;
            }

            // MODIFIES: this
            // EFFECTS: loads tickets if load tickets button is pressed. Sets load tickets window visibility to false
            //          and initializes main window
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Load Tickets".equals(button.getText())) {
                    loadTickets();
                }
                loadTickets.setVisible(false);
                initializeMainWindowGraphics();
            }

            // This method references code from this repo
            // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
            // MODIFIES: tickets
            // EFFECTS: reads tickets from json file and sets them as tickets
            private void loadTickets() {
                try {
                    tickets = jsonReader.readMyTickets();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // listener for both buttons in save showings window
    class SaveShowingsListener implements ActionListener {
        private JButton button;
        private boolean shouldSaveTickets;

        // MODIFIES: this
        // EFFECTS: sets this.button to button
        public SaveShowingsListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: if save showings button is pressed, creates new JFrame for save tickets. Else, exits system
        @Override
        public void actionPerformed(ActionEvent e) {
            saveShowings.setVisible(false);

            if ("Save Showings".equals(button.getText())) {
                JFrame saveTickets = new JFrame("Save Tickets To File");
                saveTickets.setLayout(new BorderLayout());
                saveTickets.setMinimumSize(new Dimension(LOAD_SAVE_WIDTH, LOAD_SAVE_HEIGHT));

                JButton saveTicketsButton = new JButton("Save Tickets");
                saveTicketsButton.setEnabled(true);
                saveTicketsButton.addActionListener(new SaveTicketsListener(saveTicketsButton));
                saveTickets.add(saveTicketsButton, BorderLayout.WEST);

                JButton dontSaveTicketsButton = new JButton("Don't Load Tickets");
                dontSaveTicketsButton.setEnabled(true);
                dontSaveTicketsButton.addActionListener(new SaveTicketsListener(dontSaveTicketsButton));
                saveTickets.add(dontSaveTicketsButton, BorderLayout.EAST);

                saveTickets.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                saveTickets.setLocationRelativeTo(null);
                saveTickets.setVisible(true);

            } else {
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        }

        // This method references code from this repo
        // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
        // MODIFIES: this
        // EFFECTS: if shouldSaveTickets true, writes both showings and tickets to file. Else just writes showings.
        private void saveShowingsAndTickets() {
            try {
                jsonWriter.open();
                if (shouldSaveTickets) {
                    jsonWriter.write(showings, tickets);
                } else {
                    jsonWriter.write(showings, new MyTickets());
                }
                jsonWriter.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // listener for both buttons in save tickets window
        class SaveTicketsListener implements ActionListener {
            private JButton button;

            // MODIFIES: this
            // EFFECTS: sets this.button to button
            public SaveTicketsListener(JButton button) {
                this.button = button;
            }

            // MODIFIES: this
            // EFFECTS: sets shouldSaveTickets to true if save tickets button is pressed, else false. Calls save
            //          showings and tickets method, exits system.
            @Override
            public void actionPerformed(ActionEvent e) {
                shouldSaveTickets = "Save Tickets".equals(button.getText());
                saveShowingsAndTickets();
                printLog(EventLog.getInstance());
                System.exit(0);
            }
        }
    }
}
