package ar.com.tacsutn.grupo1.eventapp.models;

import java.util.List;

public class EventList {
    private Long id;

    private String name;

    private List<Event> events;

    public EventList(Long id, String name, List<Event> eventList) {
        this.id = id;
        this.name = name;
        this.events = eventList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> eventList) {
        this.events = eventList;
    }
}