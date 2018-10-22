package ar.com.tacsutn.grupo1.eventapp.services;

import ar.com.tacsutn.grupo1.eventapp.client.EventbriteClient;
import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.repositories.EventListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventListService {

    private final EventListRepository eventListRepository;
    private final EventbriteClient eventbriteClient;

    @Autowired
    public EventListService(
            EventListRepository eventListRepository,
            EventbriteClient eventbriteClient) {

        this.eventListRepository = eventListRepository;
        this.eventbriteClient = eventbriteClient;
    }

    @Transactional
    public Optional<EventList> getById(String id) {
        return eventListRepository.findById(id);
    }

    @Transactional
    public Optional<EventList> getById(User user, String id) {
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
    public List<EventList> findListsWithEvent(EventId eventId) {
        return eventListRepository.findAllByEvents(eventId);
    }

    @Transactional
    public Page<EventId> getListEvents(EventList eventList, Pageable pageable) {
        return Utils.listToPage(new ArrayList<>(eventList.getEvents()), pageable);
    }

    @Transactional
    public EventList save(EventList eventList) {
        return eventListRepository.save(eventList);
    }

    @Transactional
    public EventList save(User user, EventList eventList) {
        eventList.setUser(user);
        return save(eventList);
    }

    @Transactional
    public void delete(User user, String id) {
        EventList eventList = getById(user, id)
                .orElseThrow(NoSuchElementException::new);

        eventListRepository.delete(eventList);
    }

    @Transactional
    public EventList rename(User user, String id, String newName) {
        EventList eventList = getById(user, id)
                .orElseThrow(NoSuchElementException::new);

        eventList.setName(newName);
        return save(eventList);
    }

    @Transactional
    public Optional<EventList> addEvent(User user, String id, EventId event) {
        return getById(user, id).map(eventList -> {
            eventList.getEvents().add(event);
            return eventListRepository.save(eventList);
        });
    }

    @Transactional
    public Page<Event> getCommonEvents(String id1, String id2, Pageable pageable) {
        EventList list1 = getById(id1)
                .orElseThrow(NoSuchElementException::new);

        EventList list2 = getById(id2)
                .orElseThrow(NoSuchElementException::new);

        List<EventId> eventIds = list1.getEvents();
        eventIds.retainAll(list2.getEvents());

        List<Event> events = eventIds
            .parallelStream()
            .flatMap(event -> eventbriteClient.getEvent(event.getId())
                .map(Stream::of)
                .orElseGet(Stream::empty))
            .collect(Collectors.toList());

        return Utils.listToPage(events, pageable);
    }

    public long getTotalEventListByUserId(String user_id){
        return eventListRepository.countAllByUserId(user_id);
    }

    public Page<EventList> getListsByUserId(User user, Pageable pageable) {
        return eventListRepository.findAllByUser(user, pageable);
    }
}
