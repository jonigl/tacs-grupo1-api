package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface EventRepository extends MongoRepository<EventId, String> {

    Long countAllByCreatedTimeIsBetween(Date from, Date to);

    Page<EventId> getAllByCreatedTimeIsBetween(Date from, Date to, Pageable pageable);
}
