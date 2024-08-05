package calendario.agendamentos.event;

import java.util.List;

import calendario.agendamentos.category.Category;

public class EventFormResponse {
    private String date;
    private List<Event> events;
    private List<Category> categories;

    public EventFormResponse(String date, List<Event> events, List<Category> categories) {
        this.date = date;
        this.events = events;
        this.categories = categories;
    }

    // Getters and setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public List<Event> getEvents() { return events; }
    public void setEvents(List<Event> events) { this.events = events; }
    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}