package ar.com.tacsutn.grupo1.eventapp.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnull;
import java.util.Date;

@Document
public class EventId implements Persistable<String> {

    @Id
    private String id;

    @CreatedDate
    private Date createdTime;

    public EventId() {

    }

    public EventId(String id) {
        this.id = id;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventId) {
            return id.equals(((EventId) obj).id);
        }
        return super.equals(obj);
    }
}
