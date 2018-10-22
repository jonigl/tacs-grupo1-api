package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventListRepository extends MongoRepository<EventList, String> {

    Page<EventList> findAll(Pageable pageable);

    Page<EventList> findAllByUser(User user, Pageable pageable);

    List<EventList> findAllByEvents(EventId event);

    long countAllByUserId(String user_id);
}
