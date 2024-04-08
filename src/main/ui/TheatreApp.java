package ui;

import model.MovieTheatre;
import model.MyTickets;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp
// Ainsleigh's 24hour theatre application
public class TheatreApp {
    private static final String JSON_STORE = "./data/movieTheatreApplication.json";
    private MovieTheatre theatre;
    private MyTickets tickets;
    private Scanner input;
    private static final String STAFF_PASSWORD = "Password";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the theatre application
    public TheatreApp() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTheatre();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTheatre() {
        boolean keepGoing = true;
        String command;

        initialTheatre();
        initialTickets();
        promptLoad();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                promptSave();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to save theatre to file
    //          if saves theatre, also prompts user to save tickets
    public void promptSave() {
        System.out.println("\nWould you like to save movie theatre to file?");
        System.out.println("\tyes -> Save to file");
        System.out.println("\tno -> Don't save");
        String response1 = input.next();
        if (response1.equals("yes")) {
            System.out.println("\nWould you like to save tickets to file?");
            System.out.println("\tyes -> Save to file");
            System.out.println("\tno -> Don't save");
            String response2 = input.next();
            if (response2.equals("yes")) {
                saveTheatreAndTickets(true);
            } else if (response2.equals("no")) {
                saveTheatreAndTickets(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to load theatre from file
    //          if loads theatre, also prompts to load tickets
    public void promptLoad() {
        System.out.println("\nWould you like to load movie theatre from file?");
        System.out.println("\tyes -> Load from file");
        System.out.println("\tno -> Don't load");
        String response1 = input.next();
        if (response1.equals("yes")) {
            System.out.println("\nWould you like to load tickets from file?");
            System.out.println("\tyes -> Load from file");
            System.out.println("\tno -> Don't load");
            String response2 = input.next();
            if (response2.equals("yes")) {
                loadTheatreAndTickets(true);
            } else if (response2.equals("no")) {
                loadTheatreAndTickets(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: process user command
    private void processCommand(String c) {
        if (c.equals("l")) {
            staffLogin();
        } else if (c.equals("c")) {
            seeCurrentMovieTitles();
        } else if (c.equals("i")) {
            seeInformationAboutMovie();
        } else if (c.equals("t")) {
            seeSpecificMovieTimes();
        } else if (c.equals("bt")) {
            bookATicket();
        } else if (c.equals("st")) {
            seeTickets();
        } else if (c.equals("m")) {
            displayMenu();
        } else {
            System.out.println("Sorry, this is not a valid command.");
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes movie theatre
    private void initialTheatre() {
        theatre = new MovieTheatre();
        input.useDelimiter("\n");
    }

    // MODIFIES: tickets
    // EFFECTS: initializes tickets
    private void initialTickets() {
        tickets = new MyTickets();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease Choose One of the Following:");
        System.out.println("\tl -> Staff Login");
        System.out.println("\tc -> See the list of current movies");
        System.out.println("\ti -> See information about a specific movie");
        System.out.println("\tt -> See all the showing times for a specific movie");
        System.out.println("\tbt -> Book a ticket");
        System.out.println("\tst -> See your tickets");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Prompts user to enter password. If correct, allows them to continue to the add or remove showing menu.
    //          Else prints "Sorry, wrong password." and prompts the to enter the password again.
    private void staffLogin() {
        System.out.println("Enter Password");
        String password = input.next();

        if (password.equals(STAFF_PASSWORD)) {
            addOrRemoveShowing();
        } else {
            System.out.println("Sorry, wrong password.");
            staffLogin();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays add or remove showing menu to user and processes the possible commands.
    private void addOrRemoveShowing() {
        System.out.println("\nWould you like to add a new movie, remove a movie and all its showings or remove a "
                + "specific showing?");
        System.out.println("\tnew -> Add a new movie");
        System.out.println("\trm -> Remove a movie");
        System.out.println("\trs -> Remove a specific showing");
        System.out.println("\tm -> Return to main menu");

        String response = input.next();

        if (response.equals("new")) {
            userAddNewMovie();
        } else if (response.equals("rm")) {
            userRemoveMovie();
        } else if (response.equals("rs")) {
            userRemoveShowing();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user for information about new movie and time movie will be shown, then adds at given time if
    //          possible.
    private void userAddNewMovie() {
        System.out.println("Type the time in 24hour time (eg. 13). It must be one of 1, 4, 7, 10, 13, 16, 19, 22");
        int time = input.nextInt();
        validTime(time);

        System.out.println("Type the title of the movie you would like to add.");
        String title = input.next();

        System.out.println("Type the rating of the movie (G, PG, 14A, 18A, R).");
        String rating = input.next();
        validRating(rating);

        System.out.println("Type the summary (250 character or less)");
        String summary = input.next();
        validSummary(summary);

        System.out.println("Type the run length in minutes (eg. 60). It must be 180 minutes or less");
        int runLength = input.nextInt();
        validRunLength(runLength);

        System.out.println("Type name(s) of the staring actor(s).");
        String staringActors = input.next();

        System.out.println("Type name of the director.");
        String director = input.next();

        System.out.println("Type the genre.");
        String genre = input.next();

        boolean added = theatre.addNewMovie((time - 1) / 3, title, rating, summary, runLength, staringActors,
                director, genre);
        correctlyAdded(added);
    }

    // EFFECTS: reports whether movie was successfully added.
    private void correctlyAdded(boolean added) {
        if (added) {
            System.out.println("Movie was successfully added!");
        } else {
            System.out.print("There is already a showing at that time.");
        }
        addOrRemoveShowing();
    }

    // EFFECTS: prints "Time is not valid!" if time is not 1, 4, 7, 10, 13, 16, 19 or 22. Else does nothing.
    private boolean validTime(int time) {
        if (time != 1 && time != 4 && time != 7 && time != 10 && time != 13 && time != 16 && time != 19 && time != 22) {
            System.out.println("Time is not valid!");
            addOrRemoveShowing();
            return false;
        }
        return true;
    }

    // EFFECTS: prints "Rating is not valid!" if rating is not G, PG, 14A, 18A or R. Else does nothing.
    private void validRating(String rating) {
        if (!rating.equals("G") && !rating.equals("PG") && !rating.equals("14A") && !rating.equals("18A")
                && !rating.equals("R")) {
            System.out.println("Rating is not valid!");
            addOrRemoveShowing();
        }
    }

    // EFFECTS: prints "Summary is not valid!" if summary is longer than 250 characters. Else does nothing.
    private void validSummary(String summary) {
        if (summary.length() > 250) {
            System.out.println("Summary is too long!");
            addOrRemoveShowing();
        }
    }

    // EFFECTS: prints "Movie is too long!" if runLength is greater than 180. Else does nothing.
    private void validRunLength(int runLength) {
        if (runLength > 180) {
            System.out.println("Movie is too long!");
            addOrRemoveShowing();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes movie with title given by user
    private void userRemoveMovie() {
        System.out.println("Type title of movie you would like to remove.");
        String title = input.next();

        if (theatre.removeMovie(title)) {
            System.out.println("Movie was successfully removed!");
        } else {
            System.out.println("This movie is not being shown in the theatre.");
        }
        addOrRemoveShowing();
    }

    // MODIFIES: this
    // EFFECTS: removes movie at time (if valid) given by user
    private void userRemoveShowing() {
        System.out.println("Type the time in 24hour time (eg. 13) of the showing you would like to remove.");
        int time = input.nextInt();
        if (!validTime(time)) {
            addOrRemoveShowing();
        }

        if (theatre.removeShowingAtTime(time)) {
            System.out.println("Showing was successfully removed!");
        } else {
            System.out.println("There is no showing at this time.");
        }
        addOrRemoveShowing();
    }

    // EFFECTS: prompts user for title of movie and prints showing times
    private void seeSpecificMovieTimes() {
        System.out.println("Type the title of the movie you would like to see the time for.");
        String title = input.next();
        String times = theatre.movieTimes(title);
        System.out.println(times);
    }

    // EFFECTS: prompts user for title of movie and prints information for it.
    private void seeInformationAboutMovie() {
        System.out.println("Type the title of the movie you would like to see more information for.");
        String title = input.next();
        List<String> info = theatre.getMovieInformation(title);
        if (info.size() == 0) {
            System.out.println("Sorry, this movie isn't in theatre!");
        }
        for (String s : info) {
            System.out.println(s);
        }
    }

    // EFFECTS: prints current movie titles
    private void seeCurrentMovieTitles() {
        System.out.println(theatre.currentMovieTitles());
    }

    // EFFECTS: prompts user for time, then books ticket for that time (if valid). Prints "Sorry, there is no movie at
    //          this time." is no movie exists.
    private void bookATicket() {
        System.out.println("Type the time (in 24hour time) of the showing. It must be one of 1, 4, 7, 10, 13, 16, 19, "
                + "22");
        int time = input.nextInt();
        if (validTime(time)) {
            int i = (time - 1) / 3;
            if (tickets.bookTicket(i, theatre)) {
                System.out.println("Your ticket has been booked!");
            } else {
                System.out.println("Sorry, there is no movie at this time.");
            }
        }
    }

    // EFFECTS: Prints string representation of tickets or print "You haven't booked any tickets." if there are no
    //          tickets.
    private void seeTickets() {
        if (tickets.numTickets() == 0) {
            System.out.println("You haven't booked any tickets.");
        } else {
            int i = 1;
            for (String s : tickets.listStringTickets()) {
                System.out.println("Ticket " + i++ + ": " + s);
            }
        }
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: if saveTickets is true, saves the theatre and tickets to file
    //          Else doesn't save tickets
    private void saveTheatreAndTickets(boolean saveTickets) {
        try {
            jsonWriter.open();
            if (saveTickets) {
                jsonWriter.write(theatre, tickets);
            } else {
                jsonWriter.write(theatre, new MyTickets());
            }
            jsonWriter.close();
            System.out.println("The movie theatre has been saved to " + JSON_STORE);
            if (saveTickets) {
                System.out.println("Your tickets have been saved to " + JSON_STORE);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: if loadTickets is true, loads theatre and tickets from file.
    //          Else only loads theatre
    private void loadTheatreAndTickets(boolean loadTickets) {
        try {
            theatre = jsonReader.readMovieTheatre();
            System.out.println("Movie theatre loaded from " + JSON_STORE);
            if (loadTickets) {
                tickets = jsonReader.readMyTickets();
                System.out.println("Tickets loaded from " + JSON_STORE);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
