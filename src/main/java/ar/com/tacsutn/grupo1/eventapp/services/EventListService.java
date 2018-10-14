package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.repositories.EventListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventListService {

    private final EventListRepository eventListRepository;

    @Autowired
    public EventListService(EventListRepository eventListRepository) {
        this.eventListRepository = eventListRepository;
    }

    @Transactional
    public Optional<EventList> getById(Long id) {
        return eventListRepository.findById(id);
    }

    @Transactional
    public Optional<EventList> getById(User user, Long id) {
        return getById(id).filter(list -> list.getUser().equals(user));
    }

    @Transactional
    public Page<EventList> getLists() {
        return getLists(PageRequest.of(0, 50));
    }

    @Transactional
    public Page<EventList> getLists(Pageable pageable) {
        return eventListRepository.findAll(pageable);
    }

    @Transactional
    public Page<EventList> getListsByUser(User user) {
        return getListsByUser(user, PageRequest.of(0, 50));
    }

    @Transactional
    public Page<EventList> getListsByUser(User user, Pageable pageable) {
        return eventListRepository.findAllByUser(user, pageable);
    }

    @Transactional
    public EventList save(EventList eventList) {
        return eventListRepository.save(eventList);
    }

    @Transactional
    public EventList save(User user, EventList eventList) {
        eventList.setUser(user);
        return eventListRepository.save(eventList);
    }

    @Transactional
    public void delete(User user, Long id) {
        EventList eventList = getById(user, id)
                .orElseThrow(NoSuchElementException::new);

        eventListRepository.delete(eventList);
    }

    @Transactional
    public EventList rename(User user, Long id, String newName) {
        EventList eventList = getById(user, id)
                .orElseThrow(NoSuchElementException::new);

        eventList.setName(newName);
        return eventListRepository.save(eventList);
    }

    @Transactional
    public Optional<EventList> addEvent(User user, Long id, EventId event) {
        return getById(user, id).map(eventList -> {
            eventList.getEvents().add(event);
            return eventListRepository.save(eventList);
        });
    }

    public Page<EventId> getCommonEvents(Long id1, Long id2) {
        EventList list1 = getById(id1)
                .orElseThrow(NoSuchElementException::new);

        EventList list2 = getById(id2)
                .orElseThrow(NoSuchElementException::new);

        List<EventId> events = list1.getEvents();
        events.retainAll(list2.getEvents());

        return new PageImpl<>(events);
    }

    public long getTotalEventListByUserId(long user_id){
        return eventListRepository.countAllByUserId(user_id);
    }

}
