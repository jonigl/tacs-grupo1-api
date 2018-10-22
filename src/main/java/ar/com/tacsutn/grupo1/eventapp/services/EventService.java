package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventListService eventListService;

    public EventService(EventRepository eventRepository, EventListService eventListService) {
        this.eventRepository = eventRepository;
        this.eventListService = eventListService;
    }

    public Optional<EventId> getById(String id){
        return eventRepository.findById(id);
    }

    public EventId save(EventId eventId) {
        return eventRepository.findById(eventId.getId())
                              .orElseGet(() -> eventRepository.save(eventId));
    }

    @Transactional
    public void removeEvent(EventList eventList, EventId event){
        eventList.removeEvent(event);
    }

    @Transactional
    public long getTotalUsersByEventId(String eventId){
        return eventRepository.findById(eventId)
                .map(event -> eventListService.findListsWithEvent(event)
                        .stream()
                        .map(list -> list.getUser().getId())
                        .distinct()
                        .count())
                .orElse(0L);
    }

    @Transactional
    public long getTotalEventsBetween(Date from, Date to) {
        return eventRepository.countAllByCreatedTimeIsBetween(from, to);
    }

    @Transactional
    public Page<EventId> getEventsBetween(Date from, Date to, Pageable pageable) {
        return eventRepository.getAllByCreatedTimeIsBetween(from, to, pageable);
    }
}
