package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.*;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
import ar.com.tacsutn.grupo1.eventapp.services.EventService;
import ar.com.tacsutn.grupo1.eventapp.services.SessionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListsController {

    private final EventListService eventListService;
    private final SessionService sessionService;
    private final EventService eventService;

    @Autowired
    public ListsController(EventListService eventListService, SessionService sessionService, EventService eventService) {
        this.eventListService = eventListService;
        this.sessionService = sessionService;
        this.eventService = eventService;
    }

    @GetMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    public EventList get(@PathVariable Long list_id, HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        return eventListService.getById(user, list_id).orElseThrow(() ->
            new ResourceNotFoundException("List not found.")
        );
    }

    @PostMapping("/lists")
    @PreAuthorize("hasRole('USER')")
    public EventList create(
            @RequestParam(name = "name") String listName,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        return eventListService.save(user, listName);
    }

    @PutMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    public EventList update(
            @PathVariable Long list_id,
            @RequestParam String name,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        EventList list = eventListService
                .getById(user, list_id)
                .orElseThrow(() -> new ResourceNotFoundException("List not found."));

        list.setName(name);
        return eventListService.save(list);
    }

    @DeleteMapping("/lists/{list_id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long list_id,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);

        try {
            eventListService.delete(user, list_id);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("List not found.");
        }
    }

    /**
     * Returns all lists of a user.
     */
    @GetMapping("/lists")
    @PreAuthorize("hasRole('USER')")
    public RestPage<EventList> getAll(Pageable pageable, HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        Page<EventList> list = eventListService.getListsByUser(user, pageable);
        return new RestPage<>(list);
    }

    /**
     * Returns all events in a list.
     * @param list_id event list identifier.
     */
    @GetMapping("/lists/{list_id}/events")
    @PreAuthorize("hasRole('USER')")
    public List<EventId> getEvents(
            @PathVariable Long list_id,
//            Pageable pageable,
            HttpServletRequest request) {

        User user = sessionService.getAuthenticatedUser(request);
        return eventListService.getById(user, list_id)
                .orElseThrow(() -> new ResourceNotFoundException("List not found."))
                .getEvents();
    }

    @PostMapping("/lists/{list_id}/events")
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public EventList addEvent(
            @PathVariable Long list_id,
            @RequestParam String event_id,
            HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        EventList eventList = eventListService.getById(user, list_id)
                .orElseThrow(() -> new ResourceNotFoundException("List not found."));
        EventId eventId = new EventId(event_id);
        eventService.save(eventId);
        eventList.getEvents().add(eventId);
        return eventListService.save(eventList);
    }

    @DeleteMapping("/lists/{list_id}/events/{event_id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEvent(@PathVariable Long list_id,
                            @PathVariable String event_id,
                            HttpServletRequest request) {
        User user = sessionService.getAuthenticatedUser(request);
        EventList eventList = eventListService.getById(user, list_id)
                .orElseThrow(() -> new ResourceNotFoundException("List not found."));
        EventId event = eventService.getById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found."));
        eventService.removeEvent(eventList, event);
        eventListService.save(eventList);
    }

}
