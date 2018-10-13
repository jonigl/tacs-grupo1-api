package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
// TODO: 12/10/2018 thinks about this feature
// @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user"}))
public class EventList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToMany
    private List<EventId> events;

    public EventList() {

    }

    public EventList(String name, User user) {
        this.name = name;
        this.user = user;
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