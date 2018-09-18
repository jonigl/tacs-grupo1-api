package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventId, String> {

}

//public class EventRepository {
//    private List<Event> events;
//
//    public EventRepository(List<Event> events) {
//        this.events = events;
//    }
//
//    public List<Event> getEvents() {
//        return events;
//    }
//
//    public List<Event> search(Criteria criteria) {
//        return this.events.stream().filter(e -> criteria.satisfies(e)).collect(Collectors.toList());
//    }
//
//    public List<Event> getEventsBetween(LocalDateTime from, LocalDateTime to) {
//        return this.events.stream().filter(e -> e.isInDateRange(from, to)).collect(Collectors.toList());
//    }
//
//    public int getEventsCountBetween(LocalDateTime from, LocalDateTime to) {
//        return this.getEventsBetween(from, to).size();
//    }
//}
