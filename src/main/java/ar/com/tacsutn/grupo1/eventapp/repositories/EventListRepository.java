package ar.com.tacsutn.grupo1.eventapp.repositories;

import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface EventListRepository extends CrudRepository<EventList, Long> {

    Page<EventList> findAll(Pageable pageable);
    Page<EventList> findAllByUser(User user, Pageable pageable);
}
