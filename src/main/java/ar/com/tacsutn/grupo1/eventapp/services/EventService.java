package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<EventId> getById(String id){
        return eventRepository.findById(id);
    }

    public EventId save(EventId eventId){
        return eventRepository.save(eventId);
    }

    @Transactional
    public void removeEvent(EventList eventList, EventId event){
        eventList.removeEvent(event);
    }

    @Transactional
    public Integer getTotalUsersByEventId(String eventId){
        return eventRepository.getTotalUsersByEventId(eventId);
    }

    @Transactional
    public long getTotalEventsBetween(Date from, Date to) {
        return eventRepository.countAllByCreatedTimeIsBetween(from, to);
    }

    @Transactional
    public Page<EventId> getIdsByEventListId(Long eventListId, Pageable pageable) {
        return eventRepository.getIdsByEventListId(eventListId, pageable);
    }
}
