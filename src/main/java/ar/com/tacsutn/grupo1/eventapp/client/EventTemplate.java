package ar.com.tacsutn.grupo1.eventapp.client;

import ar.com.tacsutn.grupo1.eventapp.client.deserializer.DatetimeDZDeserializer;
import ar.com.tacsutn.grupo1.eventapp.client.deserializer.MultipartTextDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventTemplate {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Status {

        @JsonProperty("canceled")
        CANCELED,

        @JsonProperty("live")
        LIVE,

        @JsonProperty("started")
        STARTED,

        @JsonProperty("ended")
        ENDED,

        @JsonProperty("completed")
        COMPLETED
    }

    /**
     * The unique identifier for the event.
     */
    private final String id;

    /**
     * The event's name.
     */
    @JsonDeserialize(using = MultipartTextDeserializer.class)
    private final String name;

    /**
     * The event's description (contents of the event page).
     */
    @JsonDeserialize(using = MultipartTextDeserializer.class)
    private final String description;

    /**
     * The URL to the event page for this event on Eventbrite.
     */
    private final String url;

    /**
     * The start time of the event.
     */
    @JsonDeserialize(using = DatetimeDZDeserializer.class)
    private final LocalDateTime start;

    /**
     * The end time of the event.
     */
    @JsonDeserialize(using = DatetimeDZDeserializer.class)
    private final LocalDateTime end;

    /**
     * When the event was created.
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime created;

    /**
     * When the event was last changed.
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime changed;

    /**
     * One of canceled, live, started, ended, completed
     */
    private final Status status;

    /**
     * The ISO 4217 currency code for this event.
     */
    private final String currency;

    /**
     * If this event doesn't have a venue and is only held online.
     */
    private final boolean onlineEvent;

    @JsonCreator
    public EventTemplate(
            @JsonProperty(value = "id", required = true)
            String id,

            @JsonProperty(value = "name", required = true)
            String name,

            @JsonProperty(value = "description", required = true)
            String description,

            @JsonProperty(value = "url", required = true)
            String url,

            @JsonProperty(value = "start", required = true)
            LocalDateTime start,

            @JsonProperty(value = "end", required = true)
            LocalDateTime end,

            @JsonProperty(value = "created", required = true)
            LocalDateTime created,

            @JsonProperty(value = "changed", required = true)
            LocalDateTime changed,

            @JsonProperty(value = "status", required = true)
            Status status,

            @JsonProperty(value = "currency", required = true)
            String currency,

            @JsonProperty(value = "online_event", required = true)
            boolean onlineEvent
    ) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.start = start;
        this.end = end;
        this.created = created;
        this.changed = changed;
        this.status = status;
        this.currency = currency;
        this.onlineEvent = onlineEvent;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public Status getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isOnlineEvent() {
        return onlineEvent;
    }
}
