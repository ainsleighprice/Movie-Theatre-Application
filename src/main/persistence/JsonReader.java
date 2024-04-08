package persistence;

import model.MovieTheatre;
import model.MyTickets;
import model.Ticket;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads theatre application (movie theatre and tickets) from JSON data in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads movie theatre from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MovieTheatre readMovieTheatre() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("movie theatre and my tickets");
        JSONObject jsonShowings = jsonArray.getJSONObject(0);
        return parseMovieTheatre(jsonShowings);
    }

    // EFFECTS: reads my tickets from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyTickets readMyTickets() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("movie theatre and my tickets");
        JSONObject jsonTickets = jsonArray.getJSONObject(1);
        return parseMyTickets(jsonTickets);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses movie theatre from JSON object and returns it
    private MovieTheatre parseMovieTheatre(JSONObject jsonObject) {
        MovieTheatre mt = new MovieTheatre();
        addShowings(mt, jsonObject);
        return mt;
    }

    // MODIFIES: mt
    // EFFECTS: parses movies from JSON object and adds them to movie theatre
    private void addShowings(MovieTheatre mt, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("showings");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(mt, nextMovie);
        }
    }

    // MODIFIES: mt
    // EFFECTS: parses movie from JSON object and adds it to movie theatre
    private void addMovie(MovieTheatre mt, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String rating = jsonObject.getString("rating");
        String summary = jsonObject.getString("summary");
        int runLength = jsonObject.getInt("run length");
        String staringActors = jsonObject.getString("staring actors");
        String director = jsonObject.getString("director");
        String genre = jsonObject.getString("genre");
        int index = jsonObject.getInt("index");
        mt.addNewMovie(index, title, rating, summary, runLength, staringActors, director, genre);
    }

    // EFFECTS: parses tickets from JSON object and returns it
    private MyTickets parseMyTickets(JSONObject jsonObject) {
        MyTickets ts = new MyTickets();
        addTickets(ts, jsonObject);
        return ts;
    }

    // MODIFIES: ts
    // EFFECTS: parses tickets from JSON object and adds them to my tickets
    private void addTickets(MyTickets ts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tickets");
        for (Object json : jsonArray) {
            JSONObject nextTicket = (JSONObject) json;
            addTicket(ts, nextTicket);
        }
    }

    // MODIFIES: ts
    // EFFECTS: parses ticket from JSON object and adds it to my tickets
    private void addTicket(MyTickets ts, JSONObject jsonObject) {
        String movieTitle = jsonObject.getString("movie title");
        int index = jsonObject.getInt("index");
        Ticket t = new Ticket(movieTitle, index);
        ts.addTicket(t);
    }
}
