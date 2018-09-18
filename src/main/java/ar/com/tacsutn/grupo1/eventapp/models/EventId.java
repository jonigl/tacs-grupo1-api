package ar.com.tacsutn.grupo1.eventapp.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Event")
public class EventId {

    @Id
    private String id;

    @ManyToMany
    private List<EventList> eventLists;

    public EventId() {

    }

    public EventId(String id, List<EventList> eventLists) {
        this.id = id;
        this.eventLists = eventLists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EventList> getEventLists() {
        return eventLists;
    }

    public void setEventLists(List<EventList> eventLists) {
        this.eventLists = eventLists;
    }
}
