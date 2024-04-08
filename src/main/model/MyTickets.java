package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// represents array list of user's tickets
public class MyTickets {
    private ArrayList<Ticket> tickets;

    // EFFECTS: constructs MyTickets as a list of tickets
    public MyTickets() {
        tickets = new ArrayList<>();
    }

    // REQUIRES: index must be less than 8
    // MODIFIES: this
    // EFFECTS: if there is a movie playing in theatre at the given index, returns true, and creates and adds ticket for
    //          movie at that time to tickets.
    //          else produces false.
    public boolean bookTicket(int index, MovieTheatre theatre) {
        Movie m = theatre.getMovieAtIndex(index);
        if (m != null) {
            Ticket t = new Ticket(m.getTitle(), index);
            tickets.add(t);

            EventLog.getInstance().logEvent(new Event("New ticket booked."));

            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds ticket to tickets
    public void addTicket(Ticket t) {
        tickets.add(t);
        EventLog.getInstance().logEvent(new Event("New ticket booked."));
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    // EFFECTS: returns number of tickets
    public int numTickets() {
        return tickets.size();
    }

    // REQUIRES: nonempty list of tickets
    // EFFECTS: return list of string representation of tickets
    public ArrayList<String> listStringTickets() {
        ArrayList<String> stringTickets = new ArrayList<>();
        for (Ticket t : tickets) {
            stringTickets.add(t.stringTicket());
        }
        return stringTickets;
    }

    // REQUIRES: given index must exist
    // MODIFIES: this
    // EFFECTS: removes ticket at index
    public void removeTicket(int index) {
        tickets.remove(index);

        EventLog.getInstance().logEvent(new Event("Removed ticket"));
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns my tickets as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tickets", ticketsToJson());
        return json;
    }

    // This method references code from this repo
    // Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns tickets in my tickets as a JSON array
    private JSONArray ticketsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ticket t : tickets) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
