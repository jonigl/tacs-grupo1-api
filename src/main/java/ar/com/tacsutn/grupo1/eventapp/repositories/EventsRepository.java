package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.Criteria;
import ar.com.tacsutn.grupo1.eventapp.models.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventsRepository {
    private List<Event> events;

    public EventsRepository(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Event> search(Criteria criteria) {
        return this.events.stream().filter(e -> criteria.satisfies(e)).collect(Collectors.toList());
    }

    public List<Event> getEventsBetween(LocalDateTime from, LocalDateTime to) {
        return this.events.stream().filter(e -> e.isInDateRange(from, to)).collect(Collectors.toList());
    }

    public int getEventsCountBetween(LocalDateTime from, LocalDateTime to) {
        return this.getEventsBetween(from, to).size();
    }
}
