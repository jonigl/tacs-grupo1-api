package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.Event;
import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EventRepository extends CrudRepository<EventId, String> {

    @Query(nativeQuery = true,
                 value = "SELECT COUNT(DISTINCT B.USER)   \n" +
                         "FROM EVENT_LIST_EVENTS A\n" +
                         "INNER JOIN EVENT_LIST B  ON A.EVENT_LISTS_ID = B.ID\n" +
                         "WHERE EVENTS_ID = ?1")
    Integer getTotalUsersByEventId(String eventId);

}
