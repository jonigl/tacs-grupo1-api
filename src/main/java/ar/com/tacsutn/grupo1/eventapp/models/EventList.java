package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document
public class EventList {

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @JsonIgnore
    @DBRef
    private User user;

    @DBRef
    private List<EventId> events = new ArrayList<>();

    public EventList() {

    }

    public EventList(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<EventId> getEvents() {
        return events;
    }

    public void setEvents(List<EventId> eventList) {
        this.events = eventList;
    }

    public void removeEvent(EventId event) {
        this.events.remove(event);
    }
}