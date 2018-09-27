package ar.com.tacsutn.grupo1.eventapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Event")
@EntityListeners(AuditingEntityListener.class)
public class EventId {

    @Id
    @Column(unique = true)
    private String id;

    @JsonIgnore
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<EventList> eventLists;

    @CreatedDate
    @Column
    private Date createdTime;

    public EventId() {

    }

    public EventId(String id) {
        this.id = id;
    }

    public EventId(String id, Date createdTime) {
        this.id = id;
        this.createdTime = createdTime;
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
