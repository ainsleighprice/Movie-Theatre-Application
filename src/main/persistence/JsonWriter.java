package persistence;

import model.MovieTheatre;
import model.MyTickets;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// This class references code from this repo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a writer that writes JSON representation of MovieTheatre and MyTickets to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: writes JSON representation of movie theatre and tickets to file
    public void write(MovieTheatre mt, MyTickets ts) {
        JSONObject json = new JSONObject();
        JSONObject json1 = mt.toJson();
        JSONObject json2 = ts.toJson();
        JSONArray jsonArray = new JSONArray();
        json.put("movie theatre and my tickets", jsonArray);
        jsonArray.put(json1);
        jsonArray.put(json2);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
