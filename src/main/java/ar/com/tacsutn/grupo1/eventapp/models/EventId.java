package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Event")
public class EventId {

    @Id
    @Column(unique = true)
    private String id;

    @JsonIgnore
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<EventList> eventLists;

    public EventId() {

    }

    public EventId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventId) {
            return id.equals(((EventId) obj).id);
        }
        return super.equals(obj);
    }
}
