package model;

import org.json.JSONObject;

// represents a movie ticket with a movie title and a time
public class Ticket {
    private String movieTitle;
    private int index;

    // REQUIRES: 0 <= index < 8
    // EFFECTS: constructs ticket with a title and index
    public Ticket(String movieTitle, int index) {
        this.movieTitle = movieTitle;
        this.index = index;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getMovieTime() {
        return index;
    }

    // EFFECTS: returns string representation of ticket
    public String stringTicket() {
        int time = (index * 3) + 1;
        return movieTitle + " at " + time + ":00";
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns ticket as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("movie title", movieTitle);
        json.put("index", index);
        return json;
    }
}
