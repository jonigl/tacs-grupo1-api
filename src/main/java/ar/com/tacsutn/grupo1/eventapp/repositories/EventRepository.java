package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface EventRepository extends CrudRepository<EventId, String> {

    @Query(nativeQuery = true,
           value = "SELECT COUNT(DISTINCT B.USER) " +
                   "FROM EVENT_LIST_EVENTS A " +
                   "INNER JOIN EVENT_LIST B ON A.EVENT_LISTS_ID = B.ID " +
                   "WHERE EVENTS_ID = ?1")
    Integer getTotalUsersByEventId(String eventId);

    Long countAllByCreatedTimeIsBetween(Date from, Date to);
}
