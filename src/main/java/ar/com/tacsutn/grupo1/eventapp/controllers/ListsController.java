package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.models.EventId;
import ar.com.tacsutn.grupo1.eventapp.models.EventList;
import ar.com.tacsutn.grupo1.eventapp.models.RestPage;
import ar.com.tacsutn.grupo1.eventapp.models.User;
import ar.com.tacsutn.grupo1.eventapp.services.EventListService;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
@Api(tags = "Lists", description = "list resources")
public class ListsController {

    private final EventListService eventListService;
    private final SessionService sessionService;

    @Autowired
    public ListsController(EventListService eventListService, SessionService sessionService) {
        this.eventListService = eventListService;
        this.sessionService = sessionService;
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
    public MockupResponse addEvent(@PathVariable Long list_id) {
        return new MockupResponse(String.format("Event added to list %d",list_id));
    }

    @DeleteMapping("/lists/{list_id}/events/{event_id}")
    @PreAuthorize("hasRole('USER')")
    public MockupResponse deleteEvent(@PathVariable Long list_id, @PathVariable Long event_id) {
        return new MockupResponse(String.format("Event %d deleted from list %d",event_id, list_id));
    }

}
