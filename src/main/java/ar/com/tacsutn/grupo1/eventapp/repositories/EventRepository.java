package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface EventRepository extends CrudRepository<EventId, String> {

    @Query(nativeQuery = true,
           value = "SELECT COUNT(DISTINCT B.USER) " +
                   "FROM EVENT_LIST_EVENTS A " +
                   "INNER JOIN EVENT_LIST B ON A.EVENT_LISTS_ID = B.ID " +
                   "WHERE EVENTS_ID = ?1;")
    Integer getTotalUsersByEventId(String eventId);

    @Query(nativeQuery = true,
           value = "SELECT * " +
                   "FROM EVENT_LIST_EVENTS ELE " +
                   "INNER JOIN EVENT E ON ELE.EVENTS_ID = E.ID " +
                   "WHERE ELE.EVENT_LISTS_ID = ?1 ",
           countQuery = "SELECT COUNT(E.EVENTS_ID) " +
                        "FROM EVENT_LIST_EVENTS E " +
                        "WHERE E.EVENT_LISTS_ID = ?1 ")
    Page<EventId> getIdsByEventListId(Long id, Pageable pageable);

    Long countAllByCreatedTimeIsBetween(Date from, Date to);
}
