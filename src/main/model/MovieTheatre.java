package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// represents a movie theatre open 24 hours a day with movies scheduled in 3 hour blocks
public class MovieTheatre {
    private ArrayList<Movie> showings;

    // EFFECTS: constructs empty movie theatre
    public MovieTheatre() {
        showings = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            showings.add(i, null);
        }
    }

    // REQUIRES: index is < 8.
    // MODIFIES: this
    // EFFECTS: If no movie currently exists at index, produces true and, creates new movie and adds it to MovieTheatre
    //          at given index. Else, produces false.
    public boolean addNewMovie(int index, String title, String rating, String summary, int runLength,
                               String staringActors, String director, String genre) {
        if (showings.get(index) == null) {
            Movie movie = new Movie(title, rating, summary, runLength, staringActors, director, genre);
            showings.set(index, movie);

            EventLog.getInstance().logEvent(new Event("Added movie " + title));

            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: If movie exists, returns true and removes all showings of movie with given title from MovieTheatre.
    //          Else, returns false.
    public boolean removeMovie(String title) {
        boolean movieExists = false;
        for (Movie m : showings) {
            if (m != null && title.equals(m.getTitle())) {
                int time = showings.indexOf(m);
                showings.set(time, null);
                movieExists = true;

                EventLog.getInstance().logEvent(new Event("Removed movie " + m.getTitle()));
            }
        }
        return movieExists;
    }

    // REQUIRES: index is < 8
    // MODIFIES: this
    // EFFECTS: If movie exists at given index, returns true and removes movie at given index. Else produces false.
    public boolean removeShowingAtTime(int index) {
        Movie m = showings.get(index);
        if (m != null) {
            showings.set(index, null);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if a movie exists at given index
    public boolean movieAtTime(int index) {
        return showings.get(index) != null;
    }

    // EFFECTS: returns string of the titles of all the movies with showings. Returns "Sorry, no movies in theatre." if
    //          all elements of MovieTheatre are null.
    public String currentMovieTitles() {
        String response = "Sorry, no movies in theatre.  ";
        String titles = "";
        ArrayList<String> duplicates = new ArrayList<>();
        for (Movie m : showings) {
            if (m != null && !duplicates.contains(m.getTitle())) {
                titles = titles + m.getTitle() + ", ";
                response = titles;
                duplicates.add(m.getTitle());
            }
        }
        int responseLength = response.length();
        return response.substring(0, responseLength - 2);
    }

    // EFFECTS: returns all times for movie with given title in MovieTheatre. Returns "Sorry, this movie is not in
    //          theatre." if no movie with given title exists.
    public String movieTimes(String title) {
        String result = "Sorry, this movie is not in theatre.  ";
        String times = "";
        for (Movie m : showings) {
            if (m != null && title.equals(m.getTitle())) {
                int i = showings.indexOf(m);
                int time = (i * 3) + 1;
                times = times + time + ":00, ";
                result = times;
            }
        }
        return result.substring(0, result.length() - 2);
    }

    // EFFECTS: returns movie information for movie with given title. If movie does not exist in MovieTheatre, returns
    //          "Sorry, this movie is not in theatre."
    public List<String> getMovieInformation(String title) {
        List<String> movieInformation = new ArrayList<>();
        for (Movie m : showings) {
            if (m != null && title.equals(m.getTitle())) {
                movieInformation = m.getInformationStrings();
            }
        }
        return movieInformation;
    }

    public ArrayList<Movie> getShowings() {
        return showings;
    }

    // EFFECTS: return movie playing at index. Return null if no movie.
    public Movie getMovieAtIndex(int i) {
        return showings.get(i);
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns movie theatre as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("showings", showingsToJson());
        return json;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns showings in this movie theatre as a JSON array
    private JSONArray showingsToJson() {
        JSONArray jsonArray = new JSONArray();
        int index = 0;

        for (Movie m : showings) {
            if (m != null) {
                jsonArray.put(m.toJson(index));
            }
            index++;
        }

        return jsonArray;
    }
}
