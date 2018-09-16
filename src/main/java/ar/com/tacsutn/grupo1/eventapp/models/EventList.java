package ar.com.tacsutn.grupo1.eventapp.models;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private long id;

    private String name;

    private User user;

    private List<Event> events;

    public EventList(long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.events = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> eventList) {
        this.events = eventList;
    }
}