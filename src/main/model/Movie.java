package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a movie with a Title, rating, summary, run length, staring actors, director and a genre
public class Movie {
    private String title;
    private String rating;     // rating according to Canada's motion picture rating system (G, PG, 14A, 18A, R)
    private String summary;
    private int runLength;     // run length in minutes
    private String staringActors;
    private String director;
    private String genre;

    // REQUIRES: summary string length =< 250
    //           rating must be one of G, PG, 14A, 18A, R
    //           run length must be < 3 hours (180 minutes)
    // EFFECTS: constructs movie with title, rating, summary, runLength, staring actors, director and genre
    public Movie(String title, String rating, String summary, int runLength, String staringActors, String director,
                 String genre) {
        this.title = title;
        this.rating = rating;
        this.summary = summary;
        this.runLength = runLength;
        this.staringActors = staringActors;
        this.director = director;
        this.genre = genre;
    }

    // EFFECT: returns all information about movie in list of string form
    public List<String> getInformationStrings() {
        List<String> information = new ArrayList<>();
        information.add("Title: " + title);
        information.add("Rating: " + rating);
        information.add("Summary: " + summary);
        information.add("Run Length: " + runLength + " minutes");
        information.add("Staring Actors: " + staringActors);
        information.add("Director: " + director);
        information.add("Genre: " + genre);
        return information;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getSummary() {
        return summary;
    }

    public int getRunLength() {
        return runLength;
    }

    public String getStaringActors() {
        return staringActors;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns movie as json object with index of position in movie theatre
    public JSONObject toJson(int index) {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("rating", rating);
        json.put("summary", summary);
        json.put("run length", runLength);
        json.put("staring actors", staringActors);
        json.put("director", director);
        json.put("genre", genre);
        json.put("index", index);
        return json;
    }
}
